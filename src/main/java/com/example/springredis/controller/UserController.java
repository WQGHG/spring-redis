package com.example.springredis.controller;

import com.example.springredis.entity.User;
import com.example.springredis.service.UserService;
import com.example.springredis.util.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wqg on 2020/9/25.
 */
@Controller
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    RedisCache redisCache;

    @RequestMapping(value = "/listAll")
    @ResponseBody
    public List<User> listAll() {
        return userService.listAll();
    }

    @RequestMapping(value = "/id")
    @ResponseBody
    public String getUserNameById(@RequestParam("id") Long id) {

        String redisId = Long.toString(id);
        String redisUserName = redisCache.get(redisId);
        if (redisUserName != null) {
            // 测试-把消息写入redis队列，延迟30秒消费
            double score = System.currentTimeMillis() / 1000 + 30;
            redisCache.zAdd("QUEUE", redisUserName, score);
            log.info("Successfully written to Redis Message Queuing");
            return redisUserName;
        }
        String dbUserName = userService.getUserNameById(id);
        redisCache.set(redisId, dbUserName);
        return dbUserName;
    }


}
