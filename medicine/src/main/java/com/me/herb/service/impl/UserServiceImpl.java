package com.me.herb.service.impl;

import com.me.herb.mapper.UserMapper;
import com.me.herb.pojo.User;
import com.me.herb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int add(User user) {
       return userMapper.add(user);
    }

    @Override
    public void delete(Integer userId) {
        userMapper.delete(userId);
    }

    @Override
    public void update(Integer userId, String username, String phone, String email) {
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setPhone(phone);
        user.setEmail(email);
        userMapper.update(user);
    }

    @Override
    public User queryById(Integer userId) {
        return userMapper.queryById(userId);
    }

    @Override
    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public User login(String identity, String password) {
        return userMapper.findUserByIdentityAndPassword(identity, password);
    }


    @Override
    public User getUserByIdentity(String identity) {
        return userMapper.findUserByIdentity(identity);
    }

    @Override
    public void updateUserPassword(Integer userId, String oldPassword, String newPassword) {
        User user = userMapper.queryById(userId);
        user = userMapper.findUserByIdentity(user.getUsername());
        System.out.println(user);
        if (!user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("原密码错误");
        }
        userMapper.updateUserPassword(userId, newPassword);
    }

}
