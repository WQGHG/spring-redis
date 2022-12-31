package com.example.springredis.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by wqg on 2020/09/18.
 */
@Component
public class RedisLock {

    @Autowired
    RedisUtil redisUtil;

    /**
     * 是否能获取redis锁； false不能， true能
     * @param key
     * @return
     */
    public Boolean getLocked(String key) {
        if (StringUtils.isEmpty(redisUtil.get(key))) {
            return true;
        }
        return false;
    }

    /**
     * 设置redis锁
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     */
    public void lock(String key, String value, long time, TimeUnit timeUnit) {
        redisUtil.set(key, value, time, timeUnit);
    }

    /**
     * 释放redis锁
     * @param key
     */
    public void unLock(String key) {
        redisUtil.delete(key);
    }


}
