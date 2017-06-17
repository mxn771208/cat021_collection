package com.pekall.fms.collection.cat021_bak;
import io.netty.buffer.ByteBuf;
import java.util.Date;
import com.pekall.fms.collection.utils.DataConvert;

/**
 * Created by maxl on 15-9-21.
 */

public final class DiTimeOfApplicabilityForPosition {
  private Date time;


  public Date getTime() {
    return time;
  }
  public String getTimeZhCnName() {
    return DataConvert.toGmt8String(time);
  }



//  public void parse(ByteBuf in){
//    byte[] seconds = new byte[3];
//    in.readBytes(seconds);
//    time = DataConvert.utcAm0IncSecondsToDate(seconds);
//  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (getClass() != o.getClass())
      return false;
    DiTimeOfApplicabilityForPosition tmp = (DiTimeOfApplicabilityForPosition) o;
    return time.equals(tmp.time);
  }
}
