package com.example.springredis.service;

import com.example.springredis.util.RedisLock;
import com.example.springredis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangqinggang on 2022/10/18.
 */
@Component
@Slf4j
public class RedisLockService {

    @Autowired
    RedisLock redisLock;

    @Autowired
    RedisUtil redisUtil;

    public void doSomethingWithRedisLock() {
        String LOCK = "LOCK";
        if (redisLock.isLocked(LOCK)) {
            log.info("can not get redis lock, nothing to do.");
            return;
        }
        log.info("get redis lock...");
        String uuid = UUID.randomUUID().toString();
        redisLock.lock(LOCK, uuid,1, TimeUnit.HOURS);
        try {
            //TODO something
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (uuid.equals(redisUtil.get(LOCK))) {
                redisLock.unLock(LOCK);
            }
        }

    }


}
