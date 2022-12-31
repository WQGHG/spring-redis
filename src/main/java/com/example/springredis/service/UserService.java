package com.example.springredis.service;

import com.example.springredis.entity.User;
import com.example.springredis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wqg on 2020/9/25.
 */
@Service
public class UserService {

    @Autowired
    public UserMapper userMapper;

    public List<User> listAll() {
        return userMapper.selectList(null);
    }

    public String getUserNameById(Long id) {
        return userMapper.selectById(id).getUserName();
    }

}
