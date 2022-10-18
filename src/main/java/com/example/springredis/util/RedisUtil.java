package com.example.springredis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 一个操作redis的工具类 key只允许为String
 *
 * Created by wangqinggang on 2020/09/16.
 */
@Component
public class RedisUtil {

    @Autowired
    RedisTemplate redisTemplate;

//=====================================通用============================================================

    /**
     * 指定一个key的过期时间 秒为单位
     * @param key
     * @param timeout 过期时间
     */
    public Boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取一个key的剩余过期时间 秒为单位
     * @param key
     * @return
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断一个key是否存在
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 移除key的过期时间，持久化保存
     * @param key
     * @return
     */
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 删除key
     * @param key
     * @return
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

//=====================================string类型============================================================

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    public String get(String key) {
        return key == null ? null : (String)redisTemplate.opsForValue().get(key);
    }

    /**
     * 插入数据
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     *  插入数据并设置过期时间 单位为秒
     * @param key
     * @param value
     * @param timeout
     */
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        if (timeout > 0) {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 同时插入一个或多个 key=value 对
     * @param keysAndValues
     */
    public void batchSet(Map<String, String> keysAndValues) {
        redisTemplate.opsForValue().multiSet(keysAndValues);
    }

    /**
     * 同时插入一个或多个 key=value 对，当且仅当所有给定 key 都不存在
     * @param keysAndValues
     * @return 已经存在返回false, 不存在返回true
     */
    public Boolean batchSetIFAbsent(Map<String, String> keysAndValues) {
        return redisTemplate.opsForValue().multiSetIfAbsent(keysAndValues);
    }

//=====================================set类型============================================================

    /**
     * 插入set
     * @param key
     * @param values
     */
    public void sSet(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 根据key获取set
     * @param key
     * @return
     */
    public Set<Object> members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取set中指定个数的元素
     * @param key
     * @param count
     * @return
     */
    public List<Object> randomMembers(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取set中一个的元素
     * @param key
     * @return
     */
    public Object randomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机弹出set中的一个元素
     * @param key
     * @return
     */
    public Object pop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 获取set的长度
     * @param key
     * @return
     */
    public Long size(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 查询某个值是否在set中
     * @param key
     * @param value
     * @return
     */
    public Boolean sHasKey(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将value从key对应的set转移到destKey对应的set中
     * @param key
     * @param value
     * @param destKey
     * @return
     */
    public boolean move(String key, Object value, String destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * 在key对应的set中删除values
     * @param key
     * @param values
     * @return 被移除的元素个数
     */
    public Long remove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 返回key和otherKey所对应的两个set的差值
     * @param key
     * @param otherKey
     * @return
     */
    public Set<Object> difference(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key,otherKey);
    }

//=====================================hash类型============================================================

    /**
     * 插入map
     * @param key
     * @param map
     */
    public void add(String key, Map<Object, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 获取map
     * @param key
     * @return
     */
    public Map<Object, Object> getHashEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 查询key对应的map中有没有hashKey
     * @param key
     * @param hashKey
     * @return
     */
    public Boolean hashKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取key对应的map中的hashKey对应的值
     * @param key
     * @param hashKey
     * @return
     */
    public Object getMapString(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 删除key对应map中的hashKeys
     * @param key
     * @param hashKeys
     * @return
     */
    public Long delete(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 使key对应的map中的hashKey对应的值增加number
     * @param key
     * @param hashKey
     * @param number long
     * @return
     */
    public Long increment(String key, Object hashKey, long number) {
        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * 使key对应的map中的hashKey对应的值增加number
     * @param key
     * @param hashKey
     * @param number double
     * @return
     */
    public Double increment(String key, Object hashKey, double number) {
        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * 获取 key对应map下的所有 hashkey 字段
     * @param key
     * @return
     */
    public Set<Object> hashKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取key对应map下面的 键值对 数量
     * @param key
     * @return
     */
    public Long hashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

//=====================================list类型============================================================

    /**
     * 在list左边添加元素
     * @param key
     * @param value
     * @return list的长度
     */
    public Long leftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 获取list指定未知的值
     * @param key
     * @param index
     * @return
     */
    public Object index(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取list指定区间的值
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> range(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 将value放在list中pivot的前面
     * @param key
     * @param pivot
     * @param value
     */
    public Long leftPush(String key, String pivot, String value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 向左边批量添加参数元素
     * @param key
     * @param values
     * @return
     */
    public Long leftPushAll(String key, String... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 在list右边添加元素
     * @param key
     * @param value
     * @return list的长度
     */
    public Long rightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向右边批量添加参数元素
     * @param key
     * @param values
     * @return
     */
    public Long rightPushAll(String key, String... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 向list添加元素假如存在
     * @param key
     * @param value
     * @return
     */
    public Long rightPushIfPresent(String key, Object value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 查询list长度
     * @param key
     * @return
     */
    public long listLength(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 弹出list左边第一个元素
     * @param key
     * @return
     */
    public Object leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 弹出左边第一个元素
     * @param key
     * @param timeout 超时时间
     * @param unit
     */
    public Object leftPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 弹出list右边第一个元素
     * @param key
     * @return
     */
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 弹出右边第一个元素
     * @param key
     * @param timeout 超时时间
     * @param unit
     */
    public Object rightPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPop(key, timeout, unit);
    }
}
