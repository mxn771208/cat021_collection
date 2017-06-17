package com.pekall.fms.redis.dao;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;


public abstract class RedisZSetDao<T> {
    @Autowired
    private RedisClient<T> redisClient;

    public abstract String getId(T t);

    public boolean add(String key, T t) {
        double time = System.currentTimeMillis();
        return redisClient.zadd(key, t, time);
    }

    public void remove(String key, T t) {
        Long id =  redisClient.zrem(key, t);
    }

    public void pop(String key) {
        Set<T> list = (Set<T>) redisClient.zrange(key, 0l, 0l, false);

        if (list != null && list.size() == 1) {
            T t = list.iterator().next();

            remove(key, t);
        }
    }

    public Set<T> all(String key) {
        return (Set<T>) redisClient.zrange(key, 0l, -1l, false);
    }

    public long size(String key) {
        return redisClient.zcard(key);
    }
}
