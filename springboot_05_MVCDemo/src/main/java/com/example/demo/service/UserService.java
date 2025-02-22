package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    // 获取单个用户
    public User getUserById(Integer id) {
        return userMapper.findById(id);
    }

    // 获取所有用户
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }
}
