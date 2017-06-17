package com.pekall.fms.collection.utils;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
/**
 * Created by maxl on 15-9-21.
 */
public class DataConvert {
  private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
  //浮点数相等精度
  public static final float FLOAT_PRECISION = 0.000001f;

  public static float toFloat(byte b[]) {
    int length = Math.min(b.length,4);
    int moveMaxBit = (length - 1) * 8;
    int tmp = 0;
    for ( int index = 0; index < length; index++ ) {
      tmp |= (int)(b[index] & 0xff) << moveMaxBit - index * 8;
    }
    return Float.intBitsToFloat(tmp);
  }

  public static byte[] getBytes(float val) {
    int tmp = Float.floatToIntBits(val);
    byte[] b = new byte[4];
    for (int i = 0; i < 4; i++) {
      b[i] = (byte)(tmp >> (24 - i * 8));
    }
    return b;
  }

  public static double toDouble(byte b[]) {
    int length = Math.min(b.length,8);
    int moveMaxBit = (length - 1) * 8;
    long tmp = 0;
    for ( int index = 0; index < length; index++ ) {
      tmp |= (long)(b[index] & 0xff) << moveMaxBit-index * 8;
    }
    return Double.longBitsToDouble(tmp);
  }

  public static byte[] getBytes(double val) {
    long tmp = Double.doubleToLongBits(val);
    byte[] b = new byte[8];
    for (int i = 0; i < 8; i++) {
      b[i] = (byte)(tmp >> (56 - i * 8));
    }
    return b;
  }

  public static int toInt(byte b[]) {
    int length = Math.min(b.length,4);
    int moveMaxBit = (length - 1) * 8;
    int tmp = 0;
    for ( int index = 0; index < length; index++ ) {
      tmp |= (int)(b[index] & 0xff) << moveMaxBit - index * 8;
    }
    return tmp;
  }

  public static int toComplementInt(byte b[]) {
    int length = Math.min(b.length,4);
    int moveMaxBit = (length - 1) * 8;
    int tmp = 0;
    if((b[0] &0x80) > 0) {
      if(length == 1)
        tmp |= 0xffffff00;
      else if(length == 2)
        tmp |= 0xffff0000;
      else if(length == 3)
        tmp |= 0xff000000;
    }
    for ( int index = 0; index < length; index++ ) {
      tmp |= (int)(b[index] & 0xff) << moveMaxBit - index * 8;
    }
    return tmp;
  }
  public static long toLong(byte b[]) {
    int length = Math.min(b.length,8);
    int moveMaxBit = (length - 1) * 8;
    long tmp = 0;
    for ( int index = 0; index < length; index++ ) {
      tmp |= (long)(b[index] & 0xff) << moveMaxBit-index * 8;
    }
    return tmp;
  }

  public static long utcAm0IncSecondsToDateValue(byte seconds[],double lsb) {
    //当天 0点的时间毫秒值
    //0x15180就是一天的秒数
    Calendar currentDate = Calendar.getInstance();
    currentDate.setTime(new Date());
    currentDate.set(Calendar.HOUR_OF_DAY, 0);
    currentDate.set(Calendar.MINUTE, 0);
    currentDate.set(Calendar.SECOND, 0);
    long curval = currentDate.getTime().getTime();
    //多字节秒的增量转换为毫秒
    long incval = 0L;
    for(int i=0;i<seconds.length;i++) {
      incval |= (seconds[i] & 0xff) << 8* (seconds.length-1-i);
    }
    double tmp =curval+incval*1000.00 * lsb;
    curval = (long)tmp;
    return curval;
  }

  /*
  * seconds:从凌晨以来进过的秒数
  * for test:
  *  Date date = DataConvert.utcAm0IncSecondsToDate(new byte[]{(byte)0x1,(byte)0x51,(byte)0x79});
     String c= DataConvert.toGmt8String(date);
  * */
  public static Date utcAm0IncSecondsToDate(byte seconds[],double lsb) {
    long value = utcAm0IncSecondsToDateValue(seconds,lsb);
    return new Date(value);
  }

  public static String toGmt8String(Date date) {
    String gmt8TimeStr="";
    format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    gmt8TimeStr = format.format(date) ;
    return gmt8TimeStr ;
  }



  /**
   * 把obj转换为string
   *
   * @param object
   * @return
   */
  public static String obj2String(Object object) {
    if (object == null)
      return "";
    return object.toString();
  }

  /**
   * 把obj转换为long
   *
   * @param object
   * @return
   */
  public static long obj2Long(Object object) {
    if (object == null)
      return -1;
    return Long.parseLong(object.toString());
  }

  /**
   * 转换obj爲boolean
   *
   * @param object
   * @return
   */
  public static boolean obj2Boolean(Object object) {
    if (object == null)
      return false;
    try {
      int result = Integer.parseInt(object.toString());
      return result != 0;
    } catch (Exception e) {
      return (Boolean) object;
    }
  }

  /**
   * 装换obj为int
   *
   * @param object
   * @return
   */
  public static int obj2Int(Object object) {
    if (object == null)
      return -1;
    return Integer.parseInt(object.toString());
  }

  /**
   * 转换obj为double
   *
   * @param object
   * @return
   */
  public static double obj2Double(Object object) {
    if (object == null)
      return 0.0;
    return Double.valueOf(object.toString());
  }

  /**
   * 转换obj为decimal
   *
   * @param object
   * @return
   */
  public static BigDecimal obj2BigDecimal(Object object) {
    if (object == null)
      return BigDecimal.ZERO;
    return BigDecimal.valueOf(obj2Double(object));
  }

  public static void main(String[] args) {
    byte aa = (byte)0xfb; //整数转换
    //0 01111110 00000000000000000000000
    float fz = -23.54f;
    byte[] fb = DataConvert.getBytes(fz);
    ByteBuf fval = Unpooled.wrappedBuffer(fb);
    String fstr = ByteBufUtil.hexDump(fval);
    float fy = DataConvert.toFloat(fb);

    float z = 23.67f;
    byte[] b = DataConvert.getBytes(z);
    ByteBuf val = Unpooled.wrappedBuffer(b);
    String str = ByteBufUtil.hexDump(val);
    float y = DataConvert.toFloat(b);
  }

}

