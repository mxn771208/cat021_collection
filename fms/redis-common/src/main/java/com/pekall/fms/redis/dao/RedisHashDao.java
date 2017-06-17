package com.pekall.fms.redis.dao;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public abstract class RedisHashDao<T> {

    @Autowired
    private RedisClient<T> redisClient;
    /**
     * 返回某个key所有的值
     *
     * @param key
     * @return
     */
    public Map<String, T> get(String key) {
        return redisClient.hgetAll(key);
    }

    public T get(String key, String field) {
        return redisClient.hget(key, field);
    }

    public void set(String key, String field, T value) {
        redisClient.hset(key, field, value);
    }

    public void set(String key, Map<String, T> param) {
        redisClient.hmset(key, param);
    }


    public void remove(String key) {
        redisClient.del(key);
    }


    public void removeField(String key,String field) {
        redisClient.hdel(key,field);
    }

    public List<Map<String, T>> all(String pattern) {
        List<Map<String, T>> hashList = new ArrayList<Map<String, T>>();
        Collection<String> list = redisClient.keys(pattern);
        for (String key : list) {
            hashList.add(get(key));
        }
        return hashList;
    }

    public long size(String pattern) {
        Collection<String> list = redisClient.keys(pattern);
        return list.size();
    }


    public void expireAt(String key, Date date) {
        Map<String, T> map = get(key);
        if (map != null) {
            redisClient.expireat(key, date.getTime());
        }
    }

    public void expire(String key, long time) {
        Map<String, T> map = get(key);
        if (map != null) {
            redisClient.expire(key, time);
        }
    }

    public Collection<String> keys(String pattern) {
        return redisClient.keys(pattern);
    }
}
