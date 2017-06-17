package com.pekall.fms.collection.cat021_bak;
import io.netty.buffer.ByteBuf;

//import io.netty.buffer.ByteBufUtil;


/**
 * Created by maxl on 15-9-22.
 */
public class AdsbDeviceInfo {
  private ByteBuf in;
  private int frameContentLength;
  private DiDataSourceId sourceIdentification;
  private DiTargetReportDescriptor targetReportDescriptor;
  //bits-16/13 Spare bits set to zero
  private DiTrackId trackId;
  private DiServiceIdentification serviceIdentification;
  private DiTimeOfApplicabilityForPosition timeOfApplicabilityForPosition;
  private DiPosition position;

  public AdsbDeviceInfo(ByteBuf in) {
    this.in = in;
    this.frameContentLength = in.readableBytes();
  }
  public int getFrameContentLength() {
    return frameContentLength;
  }
  public DiDataSourceId getSourceIdentification() {
     return sourceIdentification;
  }
  public DiTargetReportDescriptor getAdsbTargetReportDescriptor() {
    return targetReportDescriptor;
  }
  public DiTrackId getTrackIdId() { return trackId; }
  public DiServiceIdentification getServiceIdentification() { return serviceIdentification; }
  public DiTimeOfApplicabilityForPosition getTimeOfApplicabilityForPosition() { return timeOfApplicabilityForPosition; }


  public boolean parse(){
    boolean result = true;
    while(in.isReadable() == true) {
      if(result == false) {
        break;
      }
      //读取FSPEC字段值
      short dataItemId = in.readShort();
      if(dataItemId == AdsbConstants.DI_DATA_SOURCE_IDENTIFICATION) {
        sourceIdentification = new DiDataSourceId();
        sourceIdentification.setSac(in.readByte());
        sourceIdentification.setSic(in.readByte());
      }
      else if(dataItemId == AdsbConstants.DI_TARGET_REPORT_DESCRIPTOR) {
        targetReportDescriptor = new DiTargetReportDescriptor();
        targetReportDescriptor.parse(in);
      }
      else if(dataItemId == AdsbConstants.DI_TRACK_ID) {
        trackId = new DiTrackId();
        trackId.setValue((short)(in.readShort() & 0xfff));
      }
      else if(dataItemId == AdsbConstants.DI_SERVER_IDENTIFICATION) {
        serviceIdentification = new DiServiceIdentification();
        serviceIdentification.setValue(in.readByte());
      }
      else if(dataItemId == AdsbConstants.DI_TIME_OF_APPLICABILITY_FOR_POSITION) {
        //timeOfApplicabilityForPosition = new DiTimeOfApplicabilityForPosition();
        //timeOfApplicabilityForPosition.parse(in);
      }
      else if(dataItemId == AdsbConstants.DI_POSITION_IN_WGS84) {
        position = new DiPosition();
        position.parse(in);
      }
      else {
        result = false;
      }
    }
    return result;
  }
}
