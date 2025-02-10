package com.me.herb.service.impl;

import com.me.herb.mapper.UserMapper;
import com.me.herb.pojo.User;
import com.me.herb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int add(User user) {
       return userMapper.add(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public User queryById(Integer id) {
        return userMapper.queryById(id);
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
    public User register(User user) {
        return null;
    }

    @Override
    public User getUserByIdentity(String identity) {
        return userMapper.findUserByIdentity(identity);
    }
}
