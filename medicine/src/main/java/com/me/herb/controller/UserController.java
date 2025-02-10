package com.me.herb.controller;

import com.me.common.Result;
import com.me.herb.pojo.User;
import com.me.herb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/user")
public class UserController {
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
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        int i = userService.add(user);
        if (i > 0) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    // 登录
    @PostMapping("login")
    public Result login(@RequestParam String identity, @RequestParam String password) {
        User user = userService.login(identity, password);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error();
        }
    }
}
