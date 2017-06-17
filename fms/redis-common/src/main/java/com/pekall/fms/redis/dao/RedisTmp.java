package com.pekall.fms.redis.dao;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
/**
 * Created by maxl on 15-11-11.
 */
public class RedisTmp extends  JedisConnectionFactory{
  private int aa;
  public void setAa(int aa) {
    this.aa = aa;
  }
  public int getAa() {
    return aa;
  }
}
