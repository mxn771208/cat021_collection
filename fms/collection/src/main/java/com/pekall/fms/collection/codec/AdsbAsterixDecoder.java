package com.pekall.fms.collection.codec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.codec.ByteToMessageDecoder;
import  com.pekall.fms.collection.cat021.*;
//import java.nio.charset.Charset;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by maxl on 15-9-22.
 */
public class AdsbAsterixDecoder extends ByteToMessageDecoder{
  private static Logger logger = LoggerFactory.getLogger(AdsbAsterixDecoder.class);
  public AdsbAsterixDecoder() {
    super();
    this.setSingleDecode(false);
  }

  @Override
  protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    Object decoded = decode(ctx, in);
    if (decoded != null) {
      out.add(decoded);
    }
  }

  protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
    if (in.readableBytes() < AdsbConstants.ADSB_IN_HEAD_LENGTH) {
      return null;
    }
    in.markReaderIndex();
    byte cat021Flag = in.readByte();
    if(cat021Flag != AdsbConstants.CAT_FLAG) {
      throw new AdsbFrameException(
        "cat flag filed is: " + cat021Flag);
    }
    int frameLength = in.readShort();
    if(frameLength < AdsbConstants.ADSB_IN_MIN_LENGTH) {
      throw new AdsbFrameException(
        "frame length filed is: " + frameLength);
    }

    frameLength -= AdsbConstants.ADSB_IN_HEAD_LENGTH;
    if (in.readableBytes() < frameLength) {
      in.resetReaderIndex();
      return null;
    }
    try {
      // extract frame
      int readerIndex = in.readerIndex();
      ByteBuf frame = extractFrame(ctx, in, readerIndex, frameLength);
      in.readerIndex(readerIndex + frameLength);

      ParserCat021 cat021Do = new ParserCat021(frame);
      Cat021Proto.Cat021Info  result = cat021Do.parse();
      frame.release();
      return result;
    }
    catch(Exception ex) {
      throw new AdsbFrameException(ex.toString());
    }
  }

  protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
    ByteBuf frame = ctx.alloc().buffer(length);
    frame.writeBytes(buffer, index, length);
    return frame;
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    throws Exception {
    if (cause instanceof  ReadTimeoutException) {
      logger.error("连接空闲超时,地址是: " + ctx.channel().remoteAddress().toString());
    }
    else if(cause instanceof AdsbFrameException) {
      logger.error("解码cat021出错: " +ctx.channel().remoteAddress().toString()+"\r\n"+cause.toString());
    }
    else {
      //super.exceptionCaught(ctx, cause);
      //cause.printStackTrace();
      //final ByteBuf data = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("protocl error!\r\n", CharsetUtil.UTF_8));
      //ctx.writeAndFlush(data);
      logger.error("未知错误: " +ctx.channel().remoteAddress().toString()+"\r\n"+cause.toString());
    }
    ctx.close();
  }
}
