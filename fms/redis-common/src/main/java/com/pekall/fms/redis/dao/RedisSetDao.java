package com.pekall.fms.redis.dao;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public abstract class RedisSetDao<T> {
    @Autowired
    private RedisClient<T> redisClient;

    public abstract String getId(T t);

    public boolean isMember(String key, T t) {
        return redisClient.sismember(key, t);
    }

    public long add(String key, T t) {
        return redisClient.sadd(key, t);
    }

    public void remove(String key, T t) {
        redisClient.srem(key,t);
    }

    public Set<T> all(String key) {
        return (Set<T>) redisClient.smembers(key);
    }

    public long size(String key) {
        return redisClient.scard(key);
    }
}
