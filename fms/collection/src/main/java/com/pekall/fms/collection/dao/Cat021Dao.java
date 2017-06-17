package com.pekall.fms.collection.dao;
import org.springframework.stereotype.Component;
import com.pekall.fms.redis.dao.RedisHashDao;
import com.pekall.fms.collection.cat021.Cat021Proto;

/**
 * Created by maxl on 15-11-11.
 */
@Component("cat021Dao")
public class Cat021Dao extends RedisHashDao<Cat021Proto.Cat021Info> {
}
