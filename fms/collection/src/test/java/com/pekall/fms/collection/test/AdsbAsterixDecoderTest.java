package com.pekall.fms.collection.test;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
//import junit.framework.Test;
//import junit.framework.Assert;
import com.pekall.fms.collection.codec.AdsbAsterixDecoder;
import  com.pekall.fms.collection.cat021_bak.*;

/**
 * Created by maxl on 15-9-23.
 */
public class AdsbAsterixDecoderTest extends TestCase{
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public AdsbAsterixDecoderTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(AdsbAsterixDecoderTest.class);
  }
  public void testAdsbAsterixDecoder() {
    ByteBuf buf = Unpooled.buffer();
    buf.writeByte(021);
    buf.writeShort(6);
    buf.writeByte(1);
    buf.writeByte(2);
    buf.writeByte(3);

    ByteBuf input = buf.duplicate();
    int aa = input.readableBytes();
    EmbeddedChannel channel = new EmbeddedChannel(new AdsbAsterixDecoder());

    ////FixedLengthFrameDecoder 会释放 ByteBuf的引用计数,duplicate方法不会增加引用计数.
    //buf.retain(); channel.writeInbound(input);
    //下面的input.readableBytes()会产生新的ByteBufer,所以不会有引用计数问题
    Assert.assertTrue(channel.writeInbound(input.readBytes(input.readableBytes())));
    Assert.assertTrue(channel.finish());
    AdsbDeviceInfo info = (AdsbDeviceInfo)channel.readInbound();
    if(info != null) {
      System.out.println("info:"+info.getSourceIdentification().getSac());
    }

//    Assert.assertEquals(buf.readBytes(3), channel.readInbound());
//    Assert.assertEquals(buf.readBytes(3), channel.readInbound());
//    Assert.assertEquals(buf.readBytes(3), channel.readInbound());
//    Assert.assertNull(channel.readInbound());
  }

}
