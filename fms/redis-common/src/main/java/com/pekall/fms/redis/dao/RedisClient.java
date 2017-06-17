/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pekall.fms.redis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisClient<T> {

  private RedisTemplate<String, T> redisTemplate;

    public RedisTemplate<String, T> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, T> redisTemplate) {
      this.redisTemplate = redisTemplate;
    }

    public synchronized void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public synchronized void hmset(String key, Map<String, T> param) {
        redisTemplate.opsForHash().putAll(key, param);
    }

    public synchronized Collection<T> hmget(String key, Collection<String> fields) {
        return redisTemplate.<String, T>opsForHash().multiGet(key, fields);
    }

    /**
     * 返回key包含的所有Field
     *
     * @param key
     * @return
     */
    public synchronized Set<String> hkeys(String key) {
        return redisTemplate.<String, T>opsForHash().keys(key);
    }

    /**
     * 返回key包含的Field个数
     *
     * @param key
     * @return
     */
    public synchronized Long hlen(String key) {
        return redisTemplate.<String, T>opsForHash().size(key);
    }

    public synchronized Long hincrBy(String key, String field, Long value) {
        return redisTemplate.<String, T>opsForHash().increment(key, field, value);
    }

    public synchronized Map<String, T> hgetAll(String key) {
        return redisTemplate.<String, T>opsForHash().entries(key);
    }

    public synchronized Boolean hexists(String key, String field) {
        return redisTemplate.<String, T>opsForHash().hasKey(key, field);
    }

    public synchronized T hget(String key, String field) {
        return redisTemplate.<String, T>opsForHash().get(key, field);
    }

    public synchronized void hdel(String key, String field) {
        redisTemplate.<String, T>opsForHash().delete(key, field);
    }

    public synchronized void hset(String key, String field, T value) {
        redisTemplate.<String, T>opsForHash().put(key, field, value);
    }

    public synchronized void quit() {
        redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                connection.close();
                return null;
            }
        });
    }

    public synchronized T get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public synchronized Collection<T> hvals(String key) {
        return redisTemplate.<String, T>opsForHash().values(key);
    }

    public synchronized Boolean hsetnx(String key, String field, T value) {
        return redisTemplate.<String, T>opsForHash().putIfAbsent(key, field, value);
    }

    public synchronized Long decr(String key) {
        return redisTemplate.opsForValue().increment(key, -1L);
    }

    public synchronized Long decrby(String key, Long value) {
        return redisTemplate.opsForValue().increment(key, -value);
    }

    public synchronized Long incr(String key) {
        return redisTemplate.opsForValue().increment(key, 1L);
    }

    public synchronized Long incrby(String key, Long value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    public synchronized String getrange(String key, Long start, Long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * value 值的长度
     *
     * @param key
     * @return
     */
    public synchronized Long strlen(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    public synchronized List<T> mget(Collection<String> fields) {
        return redisTemplate.opsForValue().multiGet(fields);
    }

    public synchronized void mset(Map<String, T> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    public synchronized void msetnx(Map<String, T> map) {
        redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    public synchronized T getset(String key, T value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    public synchronized Boolean setnx(String key, T value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public synchronized void setex(String key, T value, Long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public synchronized void setex(String key, T value, Long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    public synchronized void setbit(final String key, final Long offset, final Boolean value) {
        redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setBit(key.getBytes(), offset, value);
                return null;
            }
        });
    }

    public synchronized Boolean getbit(final String key, final Long offset) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {

                return connection.getBit(key.getBytes(), offset);
            }
        });
    }

    public synchronized Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    public synchronized void multi() {
        redisTemplate.multi();
    }

    public synchronized void unwatch() {
        redisTemplate.unwatch();
    }

    public synchronized void discard() {
        redisTemplate.discard();
    }

    public synchronized void exec() {
        redisTemplate.exec();
    }

    public synchronized void watch(Collection<String> keys) {
        redisTemplate.watch(keys);
    }

    public synchronized Long sadd(String key, T value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    public synchronized Long scard(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    public synchronized Set<T> sdiff(String key, Collection<String> keys) {
        return redisTemplate.opsForSet().difference(key, keys);
    }

    public synchronized void sdiffstore(String key, Collection<String> keys, String destinations) {
        redisTemplate.opsForSet().differenceAndStore(key, keys, destinations);
    }

    public synchronized Set<T> sinter(String key, Collection<String> keys) {
        return redisTemplate.opsForSet().intersect(key, keys);
    }

    public synchronized void sinterstore(String key, Collection<String> keys, String destination) {
        redisTemplate.opsForSet().intersectAndStore(key, keys, destination);
    }

    public synchronized Boolean sismember(String key, T value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public Set<T> smembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public Boolean smove(String key, T value, String destination) {
        return redisTemplate.opsForSet().move(key, value, destination);
    }

    public synchronized T spop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    public synchronized T srandmember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    public synchronized Long srem(String key, T value) {
        return redisTemplate.opsForSet().remove(key, value);
    }

    public synchronized Set<T> sunion(String key, Collection<String> keys) {
        return redisTemplate.opsForSet().union(key, keys);
    }

    public synchronized void sunionstore(String key, Collection<String> keys, String destination) {
        redisTemplate.opsForSet().unionAndStore(key, keys, destination);
    }

    public synchronized String echo(final String value) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return new String(connection.echo(value.getBytes()));
            }
        });
    }

    public synchronized String ping() {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
    }

    public synchronized void publish(String channel, T message) {
        redisTemplate.convertAndSend(channel, message);
    }

    public synchronized T lpop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public synchronized T blpop(String key, Long timeout) {
        return redisTemplate.opsForList().leftPop(key, timeout, TimeUnit.SECONDS);
    }

    public synchronized T brpoplpush(String key, String destination, Long timeout) {
        return redisTemplate.opsForList().rightPopAndLeftPush(key, destination, timeout, TimeUnit.SECONDS);
    }

    public synchronized T rpoplpush(String key, String destination) {
        return redisTemplate.opsForList().rightPopAndLeftPush(key, destination);
    }

    public synchronized T lindex(String key, Long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    public synchronized Long linsert(String key, T value, String pivot, String position) {
        if ("BEFORE".equals(position)) {
            return redisTemplate.opsForList().leftPush(key, (T) pivot, value);
        } else if ("AFTER".equals(position)) {
            return redisTemplate.opsForList().rightPush(key, (T) pivot, value);
        } else {
            throw new IllegalArgumentException("Wrong position: " + position);
        }
    }

    public synchronized T rpop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    public synchronized T brpop(String key, Long timeout) {
        return redisTemplate.opsForList().rightPop(key, timeout, TimeUnit.SECONDS);
    }

    public synchronized Long llen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public synchronized List<T> lall(String key) {
        long length = llen(key);
        return redisTemplate.opsForList().range(key, 0, length);
    }

    public synchronized List<T> lrange(String key, Long start, Long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public synchronized Long lrem(String key, T value, Long count) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    public synchronized void lset(String key, T value, Long index) {
        redisTemplate.opsForList().set(key, index, value);
    }

    public synchronized void ltrim(String key, Long start, Long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    public synchronized Long rpush(String key, T value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public synchronized Long rpushx(String key, T value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    public synchronized Long lpush(String key, T value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    public synchronized void del(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    public synchronized void del(String key) {
        redisTemplate.delete(key);
    }

    public synchronized Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public synchronized Boolean expire(String key, Long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public synchronized Boolean expireat(String key, Long seconds) {
        return redisTemplate.expireAt(key, new Date(seconds * 1000L));
    }

    public synchronized Collection<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public synchronized Boolean move(String key, Integer db) {
        return redisTemplate.move(key, db);
    }

    public synchronized Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    public synchronized Boolean pexpire(String key, Long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
    }

    public synchronized Boolean pexpireat(String key, Long millis) {
        return redisTemplate.expireAt(key, new Date(millis));
    }

    public synchronized String randomkey() {
        return redisTemplate.randomKey();
    }

    public synchronized void rename(String key, String value) {
        redisTemplate.rename(key, value);
    }

    public synchronized Boolean renamenx(String key, String value) {
        return redisTemplate.renameIfAbsent(key, value);
    }

    public synchronized Long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    public synchronized String type(String key) {
        return redisTemplate.type(key).toString();
    }

    public synchronized List<T> sort(String key) {
        SortQuery<String> sortQuery = SortQueryBuilder.sort(key).build();
        return redisTemplate.sort(sortQuery);
    }

    public synchronized Boolean zadd(String key, T value, Double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    public synchronized Long zcard(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    public synchronized Long zcount(String key, Double min, Double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    public synchronized Double zincrby(String key, T value, Double increment) {
        return redisTemplate.opsForZSet().incrementScore(key, value, increment);
    }

    public synchronized void zinterstore(String key, Collection<String> keys, String destination) {
        redisTemplate.opsForZSet().intersectAndStore(key, keys, destination);
    }

    public synchronized Object zrange(String key, Long start, Long end, Boolean withScore) {
        if (withScore != null && withScore) {
            return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
        }
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public synchronized Set<T> zrangebyscore(String key, Double min, Double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public synchronized Long zrank(String key, T value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    public synchronized Long zrem(String key, T value) {
        return redisTemplate.opsForZSet().remove(key, value);
    }

    public synchronized void zremrangebyrank(String key, Long start, Long end) {
        redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    public synchronized void zremrangebyscore(String key, Long start, Long end) {
        redisTemplate.opsForZSet().removeRangeByScore(key, start, end);
    }

    public synchronized Object zrevrange(String key, Long start, Long end, Boolean withScore) {
        if (withScore != null && withScore) {
            return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        }

        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public synchronized Set<T> zrevrangebyscore(String key, Double min, Double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    public synchronized Long zrevrank(String key, T value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    public synchronized void zunionstore(String key, Collection<String> keys, String destination) {
        redisTemplate.opsForZSet().unionAndStore(key, keys, destination);
    }

    public synchronized long dbSize() {
        return redisTemplate.getConnectionFactory().getConnection().dbSize();
    }

    public synchronized void flushDb() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    public synchronized void bgSave() {
        redisTemplate.getConnectionFactory().getConnection().bgSave();
    }

    public synchronized Properties info() {
        return redisTemplate.getConnectionFactory().getConnection().info();
    }
}
