package com.example.springredis.service;

import com.example.springredis.util.RedisLock;
import com.example.springredis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by wqg on 2022/10/18.
 */
@Component
@Slf4j
public class ConsumeRedisQueueService {

    @Autowired
    RedisLock redisLock;

    @Autowired
    RedisUtil redisUtil;

    public void consumeRedisQueue() {
        String LOCK = "LOCK";
        String KEY = "QUEUE";

        while (true) {
            String uuid = UUID.randomUUID().toString();

            try {
                if (!redisLock.getLocked(LOCK)) {
                    log.info("can not get redis lock");
                    Thread.sleep(1000);
                    continue;
                }
                redisLock.lock(LOCK, uuid, 1, TimeUnit.HOURS);
                Set<Object> set = redisUtil.zGet(KEY, 0, System.currentTimeMillis() / 1000);
                if (!CollectionUtils.isEmpty(set)) {
                    // 每次删除一个元素, 防止出现重复消费或者消费失败
                    set.forEach(value -> {
                        log.info("User name is {}", value);
                        redisUtil.zRemove(KEY, value);
                    });
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            } finally {
                if (uuid.equals(redisUtil.get(LOCK))) {
                    redisLock.unLock(LOCK);
                }
            }
        }

    }

}
