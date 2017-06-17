package com.pekall.fms.collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pekall.fms.collection.utils.JsonMapper;

/**
 * Created by maxl on 15-9-24.
 */
public class ServerConfig {
  private static Logger logger = LoggerFactory.getLogger(ServerConfig.class);

  //监听端口,默认9999
  private int collectionport = 9999;
  //采集数据超时时间,单位秒,默认10秒
  private int collectionTimeout = 10;

  //消息系统主机
  private String msgSystemHost;

  //消息系统端口
  private int msgSystemPort;


  public int getCollectionTimeout() {
    return collectionTimeout;
  }
  public void setCollectionTimeout(int collectionTimeout) {
    this.collectionTimeout = collectionTimeout;
  }

  public int getCollectionPort() {
    return collectionport;
  }
  public void setCollectionPort(int collectionport) {
    this.collectionport = collectionport;
  }

  public String getMsgSystemHost() {
    return msgSystemHost;
  }
  public void setMsgSystemHost(String msgSystemHost) {
    this.msgSystemHost = msgSystemHost;
  }

  public int getMsgSystemPort() {
    return msgSystemPort;
  }
  public void setMsgSystemPort(int msgSystemPort) {
    this.msgSystemPort = msgSystemPort;
  }


  @Override
  public String toString() {
    return JsonMapper.toJson(this);
  }
}
