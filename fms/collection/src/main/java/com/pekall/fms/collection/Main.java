package com.pekall.fms.collection;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import com.pekall.fms.collection.utils.DataConvert;
/**
 * Created by maxl on 15-10-15.
 */
public class Main {
  private static Logger logger = LoggerFactory.getLogger(Main.class);
  public static void main(String[] args) {
//    float fz = -23.54f;
//    byte[] fb = DataConvert.getBytes(fz);
//    ByteBuf fval = Unpooled.wrappedBuffer(fb);
//    String fstr = ByteBufUtil.hexDump(fval);
//    float fy = DataConvert.toFloat(fb);
//
//    float z = 23.67f;
//    byte[] b = DataConvert.getBytes(z);
//    ByteBuf val = Unpooled.wrappedBuffer(b);
//    String str = ByteBufUtil.hexDump(val);
//    float y = DataConvert.toFloat(b);



    CollectionServer.applicationContext = new ClassPathXmlApplicationContext(
      new String[] {"classpath*:applicationContext.xml"});
    CollectionServer server = (CollectionServer)CollectionServer.applicationContext.getBean("collectionServer");

    logger.info("Usage:"+ CollectionServer.class.getSimpleName() + " config info:\r\n" +
      server.getConfig().toString());
    try {
      server.start();
    } catch (InterruptedException ex) {
      logger.error(ex.toString());
    }
  }
}
