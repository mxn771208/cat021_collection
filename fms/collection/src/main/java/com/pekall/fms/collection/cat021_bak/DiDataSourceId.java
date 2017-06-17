package com.pekall.fms.collection.cat021_bak;
/**
 * Created by maxl on 15-9-21.
 */
//数据源识别
public final class DiDataSourceId {
  //系统区域代码
  private byte sac;
  //系统识别代码
  private byte sic;

  public byte getSac() {
    return sac;
  }
  public void setSac(byte sac) {
    this.sac = sac;
  }
  //reference http://www.eurocontrol.int/services/system-area-code-list
  //获取区域代码英文名
  public String getSacEnName() {
    String val = "";
    int tmp = 0xff & sac;
    if(0x00 == tmp)
      val = "LocalAirport";
    else if(0x02 == tmp)
      val = "Greece";
    else if(0x04 == tmp)
      val = "The Netherlands";
    else if(0x06 == tmp)
      val = "Belgium";
    else if(0x07 == tmp)
      val = "NATO";
    else if(0x08 == tmp || 0x09 == tmp)
      val = "France";
    else if(0x11 == tmp)
      val = "Ukraine";
    else if(0x12 == tmp)
      val = "Monaco";
    else if(0x14 == tmp)
      val = "Spain";
    else if(0x16 == tmp)
      val = "Hungary";
    else if(0x17 == tmp)
      val = "Bosnia-Herzegovina";
    else if(0x18 == tmp)
      val = "FYROM";
    else if(0x19 == tmp)
      val = "Croatia";
    else if(0x20 == tmp)
      return "Serbia";
    else if(0x21 == tmp)
      val = "Montenegro";
    else if(0x22 == tmp)
      val = "Italy";
    else if(0x24 == tmp)
      val = "Albania";
    else if(0x26 == tmp)
      val = "Romania";
    else if(0x28 == tmp)
      val = "Switzerland";
    else if(0x30 == tmp)
      val = "Slovak Republic";
    else if(0x31 == tmp)
      val = "Czech Republic";
    else if(0x32 == tmp)
      val = "Austria";
    else if(0x34 == tmp || 0x35 == tmp)
      val = "United Kingdom";
    else if(0x38 == tmp)
      val = "Denmark";
    else if(0x40 == tmp)
      val = "Sweden";
    else if(0x41 == tmp)
      val = "Sweden(MIL)";
    else if(0x42 == tmp)
      val = "Norway";
    else if(0x43 == tmp)
      val = "Norway(MIL)";
    else if(0x44 == tmp)
      val = "Finland";
    else if(0x45 == tmp)
      val = "Finland(MIL)";
    else if(0x46 == tmp)
      val = "Lithuania";
    else if(0x47 == tmp)
      val = "Latvia";
    else if(0x48 == tmp)
      val = "Estonia";
    else if(0x49 == tmp)
      val = "Russia North-Western Federal District";
    else if(0x50 == tmp)
      val = "Russia Central Federal District";
    else if(0x51 == tmp)
      val = "Russia Far-Eastern Federal District";
    else if(0x52 == tmp)
      val = "Tajikistan";
    else if(0x53 == tmp)
      val = "Russia Southern & North-Caucasian Federal Districts";
    else if(0x54 == tmp)
      val = "Russia Volga Federal District";
    else if(0x55 == tmp)
      val = "Russia Urals Federal District";
    else if(0x56 == tmp)
      val = "Russia Siberian Federal District";
    else if(0x57 == tmp)
      val = "Belarus";
    else if(0x60 == tmp)
      val = "Poland";
    else if(0x61 == tmp || 0x62 == tmp)
      val = "Germany";
    else if(0x63 == tmp)
      val = "Germany(Weather Service)";
    else if(0x64 == tmp)
      val = "Germany(MIL)";
    else if(0x65 == tmp)
      val = "Algeria";
    else if(0x66 == tmp)
      val = "Morocco";
    else if(0x67 == tmp)
      val = "Tunisia";
    else if(0x68 == tmp)
      val = "Portugal";
    else if(0x70 == tmp)
      val = "Luxembourg";
    else if(0x72 == tmp)
      val = "Ireland";
    else if(0x74 == tmp)
      val = "Iceland";
    else if(0x76 == tmp)
      val = "France(MIL)";
    else if(0x78 == tmp)
      val = "Malta";
    else if(0x80 == tmp)
      val = "Cyprus";
    else if(0x82 == tmp)
      val = "Armenia";
    else if(0x84 == tmp)
      val = "Bulgaria";
    else if(0x86 == tmp)
      val = "Turkey";
    else if(0x88 == tmp)
      val = "Georgia";
    else if(0x90 == tmp)
      val = "Turkmenistan";
    else if(0x93 == tmp)
      val = "Republic of Slovenia";
    else if(0x94 == tmp)
      val = "Moldova";
    else if(0x95 == tmp)
      val = "Uzbekistan";
    else if(0x97 == tmp)
      val = "Kazakhstan";
    else if(0x98 == tmp)
      val = "Kyrgyz Republic";
    else if(0x99 == tmp)
      val = "Azerbaijan";
    return val;
  }
  //获取区域代码简体中文名
  public String getSacZhCnName() {
    String val = "";
    int tmp = 0xff & sac;
    if(0x00 == tmp)
      val = "当地机场";
    else if(0x02 == tmp)
      val = "希腊";
    else if(0x04 == tmp)
      val = "荷兰";
    else if(0x06 == tmp)
      val = "比利时";
    else if(0x07 == tmp)
      val = "北大西洋公约组织";
    else if(0x08 == tmp || 0x09 == tmp)
      val = "法国";
    else if(0x11 == tmp)
      val = "乌克兰";
    else if(0x12 == tmp)
      val = "摩纳哥";
    else if(0x14 == tmp)
      val = "西班牙";
    else if(0x16 == tmp)
      val = "匈牙利";
    else if(0x17 == tmp)
      val = "波斯尼亚和黑塞哥维那";
    else if(0x18 == tmp)
      val = "马其顿共和国";
    else if(0x19 == tmp)
      val = "克罗地亚";
    else if(0x20 == tmp)
      val = "塞尔维亚";
    else if(0x21 == tmp)
      val = "黑山";
    else if(0x22 == tmp)
      val = "意大利";
    else if(0x24 == tmp)
      val = "阿尔巴尼亚";
    else if(0x26 == tmp)
      val = "罗马尼亚";
    else if(0x28 == tmp)
      val = "瑞士";
    else if(0x30 == tmp)
      val = "斯洛伐克共和国";
    else if(0x31 == tmp)
      val = "捷克";
    else if(0x32 == tmp)
      val = "奥地利";
    else if(0x34 == tmp || 0x35 == tmp)
      val = "联合王国";
    else if(0x38 == tmp)
      val = "丹麦";
    else if(0x40 == tmp)
      val = "瑞典";
    else if(0x41 == tmp)
      val = "瑞典(MIL)";
    else if(0x42 == tmp)
      val = "挪威";
    else if(0x43 == tmp)
      val = "挪威(MIL)";
    else if(0x44 == tmp)
      val = "芬兰";
    else if(0x45 == tmp)
      val = "芬兰(MIL)";
    else if(0x46 == tmp)
      val = "立陶宛";
    else if(0x47 == tmp)
      val = "拉脱维亚";
    else if(0x48 == tmp)
      val = "爱沙尼亚";
    else if(0x49 == tmp)
      val = "俄罗斯西北联邦区";
    else if(0x50 == tmp)
      val = "俄中央联邦区";
    else if(0x51 == tmp)
      val = "俄罗斯远东联邦区";
    else if(0x52 == tmp)
      val = "塔吉克斯坦";
    else if(0x53 == tmp)
      val = "俄罗斯南部和北高加索联邦区";
    else if(0x54 == tmp)
      val = "俄罗斯伏尔加联邦管区";
    else if(0x55 == tmp)
      val = "俄罗斯乌拉尔联邦区";
    else if(0x56 == tmp)
      val = "俄罗斯西伯利亚联邦区";
    else if(0x57 == tmp)
      val = "Belarus";
    else if(0x60 == tmp)
      val = "波兰";
    else if(0x61 == tmp || 0x62 == tmp)
      val = "德国";
    else if(0x63 == tmp)
      val = "德国(气象服务)";
    else if(0x64 == tmp)
      val = "德国(MIL)";
    else if(0x65 == tmp)
      val = "阿尔及利亚";
    else if(0x66 == tmp)
      val = "摩洛哥";
    else if(0x67 == tmp)
      val = "突尼斯";
    else if(0x68 == tmp)
      val = "Portugal";
    else if(0x70 == tmp)
      val = "卢森堡";
    else if(0x72 == tmp)
      val = "爱尔兰";
    else if(0x74 == tmp)
      val = "冰岛";
    else if(0x76 == tmp)
      val = "法国(MIL)";
    else if(0x78 == tmp)
      val = "马耳他";
    else if(0x80 == tmp)
      val = "塞浦路斯";
    else if(0x82 == tmp)
      val = "亚美尼亚";
    else if(0x84 == tmp)
      val = "保加利亚";
    else if(0x86 == tmp)
      val = "Turkey";
    else if(0x88 == tmp)
      val = "格鲁吉亚";
    else if(0x90 == tmp)
      val = "土库曼斯坦";
    else if(0x93 == tmp)
      val = "斯洛文尼亚共和国";
    else if(0x94 == tmp)
      val = "摩尔多瓦";
    else if(0x95 == tmp)
      val = "乌兹别克斯坦";
    else if(0x97 == tmp)
      val = "Kazakhstan";
    else if(0x98 == tmp)
      val = "吉尔吉斯共和国";
    else if(0x99 == tmp)
      val = "阿塞拜疆";
    return val;
  }


  public byte getSic() {
    return sic;
  }
  public void setSic(byte sic) {
    this.sic = sic;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    //if (!(o instanceof DiDataSourceId)) return false;
    if (getClass() != o.getClass())
      return false;
    DiDataSourceId tmp = (DiDataSourceId) o;
    return sac == tmp.sac && sic == tmp.sic;
  }
}
