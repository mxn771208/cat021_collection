package com.pekall.fms.collection.cat021_bak;
import io.netty.buffer.ByteBuf;

/**
 * Created by maxl on 15-9-22.
 * 目标报告描述符,必须的字段
 */
public final class DiTargetReportDescriptor {
  //主字段部分
  //Address Type
  //0:CAO address
  //1:Duplicate address
  //2:Surface vehicle address
  //3: Anonymous address
  //4-7 Reserved for future use
  private byte atp;
  //(ARC) Altitude Reporting Capability
  //0:25 ft
  //1:100 ft
  //2:Unknown
  //3:Invalid
  private byte arc;
  //(RC) Range Check
  //0:Default
  //1:Range Check passed,CPR Validation pending
  private byte rc;
  //(RAB) Report Type
  //0:Report from target transponder
  //1:Report from field monitor (fixed transponder)
  private byte rab ;
  FirstSub firstSub;
  SecondSub secondSub;

  public String getAtpEnName() {
    String val = "";
    int tmp = 0xff & atp;
    if(0 == tmp)
      val = "24-Bit ICAO address";
    else if(1 == tmp)
      val = "Duplicate address";
    else if(2 == tmp)
      val = "Surface vehicle address";
    else if(3 == tmp)
      val = "Anonymous address";
    return val;
  }
  public String getArcEnName() {
    String val = "";
    int tmp = 0xff & arc;
    if(0 == tmp)
      val = "25 ft";
    else if(1 == tmp)
      val = "100 ft";
    else if(2 == tmp)
      val = "Unknown";
    else if(3 == tmp)
      val = "Invalid";
    return val;
  }
  public String getRcEnName() {
    String val = "";
    int tmp = 0xff & arc;
    if(0 == tmp)
      val = "Default";
    else if(1 == tmp)
      val = "Range Check passed CPR Validation pending";
    return val;
  }
  public String getRabEnName() {
    String val = "";
    int tmp = 0xff & arc;
    if(0 == tmp)
      val = "Report from target transponder";
    else if(1 == tmp)
      val = "Report from field monitor";
    return val;
  }
  public byte getAtp() {
    return atp;
  }
  public byte getArc() {
    return arc;
  }
  public byte getRc() {
    return rc;
  }
  public byte getRab() {
    return rab;
  }
  public FirstSub getFirstSub() {
    return firstSub;
  }
  public SecondSub getSecondSub() {
    return secondSub;
  }

  public class FirstSub {
    //微分修正
    public boolean hasDcr;
    //接地位设置
    public boolean hasGbs;
    //目标报告 0:实际目标 1:模拟目标
    public byte sim;
    //目标用途 0:默认目标 1:测试目标
    public byte tst;
    //选定高度 true:能提供选定高度，false:不能提供选定高度
    public boolean hasSaa;
    //保密等级 0:有效 1:不信任 2:无信息 3:暴露
    public byte cl;

    public String getDcrEnName() {
      return hasDcr?"Differential correction":"No differential correction";
    }
    public String getGbsEnName() {
      return hasDcr?"Ground Bit set":"Ground Bit not set";
    }
    public String getSimEnName() {
      return sim==0?"Actual target report":"Simulated target report";
    }
    public String getTstEnName() {
      return tst==0?"Default":"Test Target";
    }
    public String getSaaEnName() {
      return hasSaa?"Equipment capable to provide Selected Altitude":
             "Equipment not capable to provide Selected Altitude";
    }
    public String getClEnName() {
      String val = "";
      int tmp = 0xff & cl;
      if(0 == tmp)
        val = "Report valid";
      else if(1 == tmp)
        val = "Report suspect";
      else if(2 == tmp)
        val = "No information";
      return val;
    }
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      //if (!(o instanceof FirstSub)) return false;
      if (getClass() != o.getClass())
        return false;
      FirstSub tmp = (FirstSub) o;
      return hasDcr == tmp.hasDcr && hasGbs == tmp.hasGbs &&
        sim == tmp.sim && tst == tmp.tst && hasSaa == tmp.hasSaa &&
        cl == tmp.cl;
    }
  }
  public class SecondSub {
    //NOGO位设置
    public boolean hasNogo;
    //Compact Position Reporting
    //false:Validation failed
    //true:Validation correct
    public boolean cprValidCorrect;
    //Local Decoding Position Jump
    //false:LDPJ not detected
    //true:LDPJ detected
    public boolean ldpjDetected;
    //Range Check
    //0:default
    //1:Range Check failed
    public byte rcf;

    public String getNogoEnName() {
      return hasNogo?"NOGO-bit set":"NOGO-bit not set";
    }
    public String getCprEnName() {
      return cprValidCorrect?"CPR Validation correct":"CPR Validation failed";
    }
    public String getLdpjEnName() {
      return ldpjDetected?"LDPJ detected":"LDPJ not detected";
    }
    public String getRcfEnName() {
      return rcf==0?"Default":"Range Check failed";
    }
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      //if (!(o instanceof SecondSub)) return false;
      if (getClass() != o.getClass())
        return false;
      SecondSub tmp = (SecondSub) o;
      return hasNogo == tmp.hasNogo && cprValidCorrect == tmp.cprValidCorrect &&
        ldpjDetected == tmp.ldpjDetected && rcf == tmp.rcf;
    }
  }

    //FX filed not process

  public void parse(ByteBuf in){
    int field = in.readByte() & 0xff;
    //处理主字段
    atp = (byte)(field >> 5);
    arc = (byte)(field >> 3 & 0x3);
    rc = (byte)(field >> 2 & 0x1);
    rab= (byte)(field >> 1 & 0x1);
    boolean fxExistSub = (field  & 0x1) == 1;
    //没有第一个子字段就结束
    if(fxExistSub == false)
      return;
    //处理#1子字段
    firstSub = new FirstSub();
    field = in.readByte() & 0xff;
    firstSub.hasDcr = (field & 0x80)>0;
    firstSub.hasGbs = (field >>6 & 0x1) == 1;
    firstSub.sim= (byte)(field >> 5 & 0x1);
    firstSub.tst= (byte)(field >> 4 & 0x1);
    firstSub.hasSaa = (field >>3 & 0x1) == 0;
    firstSub.cl = (byte)(field >> 1 & 0x3);
    fxExistSub = (field  & 0x1) == 1;
    //没有第二个子字段就结束
    if(fxExistSub == false)
      return;
    //处理#2子字段
    field = in.readByte() & 0xff;
    secondSub = new SecondSub();
    secondSub.hasNogo = (field >>4 & 0x1) == 1;
    secondSub.cprValidCorrect = (field >>3 & 0x1) == 0;
    secondSub.ldpjDetected = (field >>2 & 0x1) == 1;
    secondSub.rcf = (byte)(field >> 1 & 0x1);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (getClass() != o.getClass())
      return false;

    DiTargetReportDescriptor tmp = (DiTargetReportDescriptor) o;
    return atp == tmp.atp && arc == tmp.arc &&
      rc == tmp.rc && rab == tmp.rab &&
      ((firstSub == null && tmp.firstSub == null) ||
       (firstSub!=null && firstSub.equals(tmp.firstSub))) &&
      ((firstSub == null && tmp.firstSub == null) ||
        (firstSub!=null && firstSub.equals(tmp.firstSub)));
  }
}
