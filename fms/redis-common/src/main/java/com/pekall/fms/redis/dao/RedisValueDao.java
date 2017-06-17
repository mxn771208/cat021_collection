package com.pekall.fms.redis.dao;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;


public abstract class RedisValueDao<T> {
    @Autowired
    private RedisClient<T> redisClient;

    public void get(String key, T t) {
        redisClient.get(key);
    }

    public void set(String key, T t) {
        redisClient.set(key, t);
    }

    public void remove(String key) {
        redisClient.del(key);
    }

    public List<T> all(String pattern) {
        Collection<String> keys = redisClient.keys(pattern);
        return redisClient.mget(keys);
    }

}

