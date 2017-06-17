package com.pekall.fms.collection.codec;
import com.pekall.fms.collection.CollectionServer;
import com.pekall.fms.collection.utils.JsonMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.RecyclableArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pekall.fms.collection.cat021.Cat021Proto;
import com.pekall.fms.collection.cat021.ParserCat021;
import com.pekall.fms.collection.dao.Cat021Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by maxl on 15-11-5.
 */

public class CommandHandler extends ChannelInboundHandlerAdapter{
  static int a = 0;
  private static Logger logger = LoggerFactory.getLogger(CommandHandler.class);
  //@Autowired
  private static Cat021Dao cat021Dao = (Cat021Dao)CollectionServer.applicationContext.getBean("cat021Dao");


  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg)
    throws Exception {
    ++a;
    Cat021Proto.Cat021Info data = (Cat021Proto.Cat021Info)msg;
    //String key = String.valueOf(data.getTargetAddress());
    //String field = String.valueOf(data.getTimeOfReportTransmission());
   // cat021Dao.set(key,field,data);
    //Cat021Proto.Cat021Info data2  = (Cat021Proto.Cat021Info)cat021Dao.get(key,field);
    //logger.info("数据是 帧号"+ a +" 内容:\r\n" + ParserCat021.toString(data2));
    logger.info(ParserCat021.toString(data));

  }
}
