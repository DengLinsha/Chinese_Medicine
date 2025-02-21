package com.me.herb.service;

import com.me.herb.pojo.User;

import java.util.List;

public interface UserService {
    // 添加用户信息
    int add(User user);

    // 删除用户信息
    void delete(Integer id);

    // 修改用户信息
    void update(User user);

    // 根据用户名/手机号/邮箱查找用户
    User queryById(Integer id);

    // 查询全部用户信息
    List<User> queryAll();

    // 登录
    User login(String username, String password);


    // 查询用户是否存在
    User getUserByIdentity(String identity);

    // 修改密码
    boolean updatePassword(String identity, String password);
}
