package com.me.herb.controller;

import com.me.common.Result;
import com.me.common.ResultEnum;
import com.me.herb.pojo.User;
import com.me.herb.service.UserService;
import com.me.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/user")
public class
UserController {
    @Autowired
    private UserService userService;

    // get请求
    @GetMapping("/{id}")
    public Result query(@PathVariable(value="id")Integer id) {
        User user = userService.queryById(id);
        return Result.success(user);
    }

    @GetMapping
    public Result queryAll() {
        List<User> users = userService.queryAll();
        return Result.success(users);
    }

    // post请求
    @PostMapping("/register")
    public Result add(@RequestBody User user) {
        try {
            String password = PasswordUtil.desEncrypt(user.getPassword());
            user.setPassword(password);
            int i = userService.add(user);
            if (i > 0) {
                return Result.success(true);
            } else {
                return Result.error("注册失败，请稍后再试");
            }
        } catch (DataIntegrityViolationException e) {
            return Result.error("用户名/电话号码/邮箱已存在！");
        } catch (Exception e) {
            // 其他异常
            return Result.error("注册失败，请稍后再试");
        }
    }

    // 登录
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginData) {
        String identity = loginData.get("identity");
        String password = PasswordUtil.desEncrypt(loginData.get("password"));
        User user = userService.login(identity, password);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error(ResultEnum.USERNAME_OR_PASSWORD_ERROR);
        }
    }

    // 根据用户名/手机号/邮箱找回密码
    @GetMapping("/findPassword")
    public Result findPassword(@RequestParam String identity) {
        User user = userService.getUserByIdentity(identity);
        if (user != null) {
            return Result.success(user.getId());
        } else {
            return Result.error("用户不存在");
        }
    }

    // 更新密码
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String, String> passwordData) {
        String identity = passwordData.get("identity");
        String password = PasswordUtil.desEncrypt(passwordData.get("password"));
        boolean success = userService.updatePassword(identity, password);
        if (success) {
            return Result.success(true);
        } else {
            return Result.error("更新密码失败");
        }
    }
}
