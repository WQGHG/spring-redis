package com.example.springredis.controller;

import com.example.springredis.entity.User;
import com.example.springredis.service.UserService;
import com.example.springredis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wangqinggang on 2020/9/25.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    RedisUtil redisUtil;

    @RequestMapping(value = "/listAll")
    @ResponseBody
    public List<User> listAll() {
        return userService.listAll();
    }

    @RequestMapping(value = "/id")
    @ResponseBody
    public String getUserNameById(@RequestParam("id") Long id) {

        String redisId = Long.toString(id);
        String redisUserName = redisUtil.get(redisId);
        if (redisUserName != null) {
            return redisUserName;
        }
        String dbUserName = userService.getUserNameById(id);
        redisUtil.set(redisId, dbUserName);
        return dbUserName;
    }


}
