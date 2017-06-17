package com.pekall.fms.collection.cat021_bak;

import com.pekall.fms.collection.utils.DataConvert;
import io.netty.buffer.ByteBuf;

/**
 * Created by maxl on 15-9-21.
 */

public final class DiPosition {
  //经度，正表示东经，反之西经
  private float longitude;
  //维度，正表示北纬，反之南纬
  private float latitude;


  public float getLongitude() {
    return longitude;
  }
  public float getLatitude() {
    return latitude;
  }



  public void parse(ByteBuf in){
    byte[] data = new byte[3];
    in.readBytes(data);
    latitude = DataConvert.toFloat(data);

    in.readBytes(data);
    longitude = DataConvert.toFloat(data);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (getClass() != o.getClass())
      return false;
    DiPosition tmp = (DiPosition) o;
    return ((longitude-tmp.longitude>-DataConvert.FLOAT_PRECISION)&&
      (latitude-tmp.latitude)<DataConvert.FLOAT_PRECISION);
  }
}
