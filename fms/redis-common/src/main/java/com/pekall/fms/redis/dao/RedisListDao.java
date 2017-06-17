package com.pekall.fms.redis.dao;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class RedisListDao<T> {

    // @Autowired
    private RedisClient<T> redisClient;

    public void setRedisClient(RedisClient<T> redisClient) {
        this.redisClient = redisClient;
    }

    public RedisClient<T> getRedisClient() {
        return redisClient;
    }

    public abstract String getId(T t);

    public long index(String key, T t) {
        long size = redisClient.llen(key);

        for (long i = 0; i < size; i++) {
            T obj =  redisClient.lindex(key, i);
            if (obj == t) {
                return i;
            }
        }

        return -1;
    }

    public long indexById(String key, String id) {
        long size = redisClient.llen(key);

        for (long i = 0; i < size; i++) {
            T obj =  redisClient.lindex(key, i);

            String objId = getId(obj);
            if (objId.equals(id)) {
                return i;
            }
        }

        return -1;
    }

    public void set(String key, long index, T t) {
        redisClient.lset(key, t, index);
    }

    public long add(String key, T t) {
        return redisClient.rpush(key, t);
    }

    public T lpop(String key) {
        return redisClient.lpop(key);
    }

    public T blpop(String key, Long timeout) {
        return redisClient.blpop(key, timeout);
    }

    public void remove(String key, T t) {
        long index = index(key, t);
        T value = redisClient.lindex(key, index);
        redisClient.lrem(key, value, index);
    }

    public void removeById(String key, String id) {
        long index = indexById(key, id);
        T value = redisClient.lindex(key, index);
        redisClient.lrem(key, value, index);
    }

    public List<T> all(String key) {
        return redisClient.lall(key);
    }

    public long size(String key) {
        return redisClient.llen(key);
    }
}
