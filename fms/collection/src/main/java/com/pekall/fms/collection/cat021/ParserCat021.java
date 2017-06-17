package com.pekall.fms.collection.cat021;

import io.netty.buffer.ByteBuf;
import com.google.protobuf.TextFormat;
import com.pekall.fms.collection.utils.DataConvert;
import java.util.Date;


/**
 * Created by maxl on 15-9-22.
 */
public class ParserCat021 {
  private ByteBuf in;
  private int frameContentLength;
  Cat021Proto.Cat021Info.Builder result = null;

  public ParserCat021(ByteBuf in) {
    this.in = in;
    this.frameContentLength = in.readableBytes();
  }

  public Cat021Proto.Cat021Info parse(){
    boolean isReadable = true;
    while(in.isReadable() == true) {
      if(isReadable == false) {
        break;
      }
      if(result == null) {
        result = Cat021Proto.Cat021Info.newBuilder();
      }
      //读取FSPEC字段值
      short dataItemId = in.readShort();
      if(dataItemId == AdsbConstants.DI_DATA_SOURCE_IDENTIFICATION) {
        result.setDataSourceIdentification(
          Cat021Proto.DiDataSourceId.newBuilder()
            .setSac(in.readByte())
            .setSic(in.readByte())
        );
      }
      else if(dataItemId == AdsbConstants.DI_TARGET_REPORT_DESCRIPTOR) {
        result.setTargetReportDescriptor(parseTargetReportDescriptor());
      }
      else if(dataItemId == AdsbConstants.DI_TRACK_ID) {
        result.setTrackId(in.readShort() & 0xfff);
      }
      else if(dataItemId == AdsbConstants.DI_SERVER_IDENTIFICATION) {
        result.setServiceIdentification(in.readByte());
      }
      else if(dataItemId == AdsbConstants.DI_TIME_OF_APPLICABILITY_FOR_POSITION) {
        result.setTimeOfApplicabilityForPosition(parseTimeOfApplicabilityForPosition());
      }
      else if(dataItemId == AdsbConstants.DI_POSITION_IN_WGS84) {
        result.setPosition(parsePosition());
      }
      else if(dataItemId == AdsbConstants.DI_HIGH_RESOLUTION_POSITION_IN_WGS84) {
        result.setHighResolutionPosition(parseHighResolutionPosition());
      }
      else if(dataItemId == AdsbConstants.DI_TIME_OF_APPLICABILITY_FOR_VELOCITY) {
        final double lsb = 1.00/128.00;
        long tmpValue = internalRead3BytesDateValue(lsb);
        //Date aa = new Date(result.getValue());
        result.setTimeOfApplicabilityForVelocity(tmpValue) ;
      }
      else if(dataItemId == AdsbConstants.DI_TIME_OF_MESSAGE_RECEPTION_OF_POSITION) {
        final double lsb = 1.00/128.00;
        long tmpValue = internalRead3BytesDateValue(lsb);
        result.setTimeOfMessageReceptionOfPosition(tmpValue);
      }
      else if(dataItemId == AdsbConstants.DI_TIME_OF_MESSAGE_RECEPTION_OF_POSITION_HIGH_PRECISION) {
        result.setTimeOfMessageReceptionOfPositionHighPrecision(parseTimeOfMessageReceptionOfPositionHighPrecision());
      }
      else if(dataItemId == AdsbConstants.DI_TIME_OF_MESSAGE_RECEPTION_OF_VELOCITY) {
        final double lsb = 1.00/128.00;
        long tmpValue = internalRead3BytesDateValue(lsb);
        //Date aa = new Date(tmpValue);
        result.setTimeOfMessageReceptionForVelocity(tmpValue);

      }
      else if(dataItemId == AdsbConstants.DI_TIME_OF_MESSAGE_RECEPTION_OF_VELOCITY_HIGH_PRECISION) {
        result.setTimeOfMessageReceptionOfVelocityHighPrecision(parseTimeOfMessageReceptionOfVelocityHighPrecision());
      }
      else if(dataItemId == AdsbConstants.DI_AIR_SPEED) {
        result.setCalculatedAirSpeed(parseCalculatedAirSpeed());
      }
      else if(dataItemId == AdsbConstants.DI_TRUE_AIR_SPEED) {
        result.setTrueAirspeed(parseTrueAirspeed());
      }
      else if(dataItemId == AdsbConstants.DI_TARGET_ADDRESS) {
        byte[] bytes = new byte[3];
        in.readBytes(bytes);
        result.setTargetAddress(DataConvert.toInt(bytes));
      }
      else if(dataItemId == AdsbConstants.DI_GEOMETRIC_Height) {
        result.setGeometricHeight(parseDiGeometricHeight());
      }
      else if(dataItemId == AdsbConstants.DI_QUALITY_INDICATORS) {
        result.setQualityIndicators(parseQualityIndicators());
      }
      else if(dataItemId == AdsbConstants.DI_MOPS_VERSION) {
        result.setMopsVersion(parseMopsVersion());
      }
      else if(dataItemId == AdsbConstants.DI_MODE_3A_CODE) {
        result.setMode3ACodeInOctalRepresentation(parseMode3aCodeInOctalRepresentation());
      }
      else if(dataItemId == AdsbConstants.DI_ROLL_ANGLE) {
        result.setRollAngle(parseRollAngle());
      }
      else if(dataItemId == AdsbConstants.DI_FLIGHT_LEVEL) {
        result.setFlightLevel(parseFlightLevel());
      }
      else if(dataItemId == AdsbConstants.DI_MAGNETIC_HEADING) {
        result.setMagneticHeading(parseMagneticHeading());
      }
      else if(dataItemId == AdsbConstants.DI_TARGET_STATUS) {
        result.setTargetStatus(parseTargetStatus());
      }
      else if(dataItemId == AdsbConstants.DI_BAROMETRIC_VERTICAL_RATE) {
        result.setBarometricVerticalRate(parseBarometricVerticalRate());
      }
      else if(dataItemId == AdsbConstants.DI_GEOMETRIC_VERTICAL_RATE) {
        result.setGeometricVerticalRate(parseGeometricVerticalRate());
      }
      else if(dataItemId == AdsbConstants.DI_GROUND_VECTOR) {
        result.setGroundVector(parseGroundVector());
      }
      else if(dataItemId == AdsbConstants.DI_TRACK_ANGLE_RATE) {
        result.setTrackAngleRate(parseTrackAngleRate());
      }
      else if(dataItemId == AdsbConstants.DI_TIME_OF_REPORT_TRANSMISSION) {
        final double lsb = 1.00/128.00;
        long tmpValue = internalRead3BytesDateValue(lsb);
        Date aa = new Date(tmpValue);
        result.setTimeOfReportTransmission(tmpValue);
      }
      else if(dataItemId == AdsbConstants.DI_TARGET_IDENTIFICATION) {
        byte[] bytes = new byte[6];
        in.readBytes(bytes);
        long tmpValue = DataConvert.toLong(bytes);
        result.setTargetIdentification(tmpValue);
      }
      else if(dataItemId == AdsbConstants.DI_EMITTER_CATEGORY) {
        byte[] bytes = new byte[1];
        in.readBytes(bytes);
        int tmpValue = DataConvert.toInt(bytes);
        result.setEmitterCategory(tmpValue);
      }
      else if(dataItemId == AdsbConstants.DI_MET_INFORMATION) {
        Cat021Proto.DiMetInformation.Builder builder = parseMetInformation();
        if(builder != null) {
          result.setMetInformation(builder);
        }
      }
      else if(dataItemId == AdsbConstants.DI_INTERMEDIATE_STATE_SELECTED_ALTITUDE) {
        result.setIntermediateStateSelectedAltitude(parseIntermediateStateSelectedAltitude());
      }
      else if(dataItemId == AdsbConstants.DI_FINAL_STATE_SELECTED_ALTITUDE) {
        result.setFinalStateSelectedAltitude(parseFinalStateSelectedAltitude());
      }
      else if(dataItemId == AdsbConstants.DI_TRAJECTORY_INTENT) {
        result.setTrajectoryIntent(parseTrajectoryIntent());
      }
      else if(dataItemId == AdsbConstants.DI_SERVICE_MANAGEMENT) {
        result.setServiceManagement(parseServiceManagement());
      }
      else if(dataItemId == AdsbConstants.DI_AIRCRAFT_OPERATIONAL_STATUS) {
        result.setAircraftOperationalStatus(parseAircraftOperationalStatus());
      }
      else if(dataItemId == AdsbConstants.DI_SURFACE_CAPABILITIES_AND_CHARACTERISTICS) {
        result.setSurfaceCapabilitiesAndCharacteristics(parseSurfaceCapabilitiesAndCharacteristics());
      }
      else if(dataItemId == AdsbConstants.DI_MESSAGE_AMPLITUDE) {
        byte[] bytes = new byte[1];
        in.readBytes(bytes);
        int tmpValue = DataConvert.toComplementInt(bytes);
        result.setMessageAmplitude(tmpValue);
      }
      else if(dataItemId == AdsbConstants.DI_MODE_S_MB_DATA) {
        result.setModeSmbData(parseModeSmbData());
      }
      else if(dataItemId == AdsbConstants.DI_ACAS_RESOLUTION_ADVISORY_REPORT) {
        byte[] bytes = new byte[7];
        in.readBytes(bytes);
        long tmpValue = DataConvert.toLong(bytes);
        result.setAcasResolutionAdvisoryReport(tmpValue);
      }
      else if(dataItemId == AdsbConstants.DI_RECEIVER_ID) {
        byte[] bytes = new byte[1];
        in.readBytes(bytes);
        int tmpValue = DataConvert.toInt(bytes);
        result.setReceiverId(tmpValue);
      }
      else if(dataItemId == AdsbConstants.DI_DATA_AGES) {
        result.setDataAges(parseDataAges());
      }
    }
    return result!=null ? result.build() : null;
  }

  public static String toString(Cat021Proto.Cat021Info info) {
    // JsonMapper.toJson(info);
    //JsonFormat.printToString(someProto);
    return TextFormat.printToString(info);
    //JsonFormat
  }

  public long internalRead3BytesDateValue(double lsb) {
    byte[] bytes = new byte[3];
    in.readBytes(bytes);
    return DataConvert.utcAm0IncSecondsToDateValue(bytes,lsb);
  }


  private Cat021Proto.DiTargetReportDescriptor.Builder parseTargetReportDescriptor() {
    Cat021Proto.DiTargetReportDescriptor.Builder builder = Cat021Proto.DiTargetReportDescriptor.newBuilder();
    int field = in.readByte() & 0xff;
    //处理主字段
    builder.setAtp(field >> 5);
    builder.setArc(field >> 3 & 0x3);
    builder.setRc(field >> 2 & 0x1);
    builder.setRab(field >> 1 & 0x1);
    boolean fxExistSub = (field  & 0x1) == 1;
    //没有第一个子字段就结束
    if(fxExistSub == false)
      return builder;
    //处理#1子字段
    Cat021Proto.DiTargetReportDescriptorFirstSub.Builder firstSub = Cat021Proto.DiTargetReportDescriptorFirstSub.newBuilder();
    field = in.readByte() & 0xff;
    firstSub.setHasDcr((field & 0x80)>0);
    firstSub.setHasGbs((field >>6 & 0x1) == 1);
    firstSub.setSim(field >> 5 & 0x1);
    firstSub.setTst(field >> 4 & 0x1);
    firstSub.setHasSaa((field >>3 & 0x1) == 0);
    firstSub.setCl(field >> 1 & 0x3);
    builder.setFirstSub(firstSub);

    fxExistSub = (field  & 0x1) == 1;
    //没有第二个子字段就结束
    if(fxExistSub == false)
      return builder;
    //处理#2子字段
    Cat021Proto.DiTargetReportDescriptorSecondSub.Builder secondSub = Cat021Proto.DiTargetReportDescriptorSecondSub.newBuilder();
    field = in.readByte() & 0xff;
    secondSub.setHasNogo((field >>4 & 0x1) == 1);
    secondSub.setCprValidCorrect((field >>3 & 0x1) == 0);
    secondSub.setLdpjDetected((field >>2 & 0x1) == 1);
    secondSub.setRcf(field >> 1 & 0x1);
    builder.setSecondSub(secondSub);

    return builder;
  }

  private long parseTimeOfApplicabilityForPosition() {
    final double lsb = 1.00/128.00;
    long tmpValue = internalRead3BytesDateValue(lsb);
    //Date aa = new Date(result.getValue());
    return tmpValue;
  }

  private Cat021Proto.DiPosition.Builder parsePosition() {
    Cat021Proto.DiPosition.Builder builder = Cat021Proto.DiPosition.newBuilder();
    byte[] data = new byte[3];
    final double lsb = 180.00/Math.pow(2, 23);
    //维度
    in.readBytes(data);
    builder.setLatitude((float)(DataConvert.toComplementInt(data)*lsb));

    //经度
    in.readBytes(data);
    builder.setLongitude((float)(DataConvert.toComplementInt(data)*lsb));
    return builder;
  }

  private Cat021Proto.DiHighResolutionPosition.Builder parseHighResolutionPosition() {
    Cat021Proto.DiHighResolutionPosition.Builder builder = Cat021Proto.DiHighResolutionPosition.newBuilder();
    byte[] data = new byte[4];
    final double lsb = 180.00/Math.pow(2, 30);
    //维度
    in.readBytes(data);
    builder.setLatitude((float)(DataConvert.toComplementInt(data)*lsb));

    //经度
    in.readBytes(data);
    builder.setLongitude((float)(DataConvert.toComplementInt(data)*lsb));
    return builder;
  }

  private long parseTimeOfMessageReceptionOfPositionHighPrecision() {
    final double lsb = 1.00/Math.pow(2, 30);
    byte[] bytes = new byte[4];
    in.readBytes(bytes);
    //取fsi
    int fsi = bytes[0] >> 6 & 0x3;
    long tmpValue;
    //清除fsi标记
    bytes[0] = (byte)(bytes[0] & 0x3f);
    tmpValue = DataConvert.utcAm0IncSecondsToDateValue(bytes,lsb);

    if(fsi == 0x0){
      //不变
      if(result.hasTimeOfApplicabilityForPosition())
        tmpValue = result.getTimeOfApplicabilityForPosition();
      else if(result.hasTimeOfMessageReceptionOfPosition())
        tmpValue = result.getTimeOfMessageReceptionOfPosition();
    }
    else if(fsi == 0x1){
      //加1秒
      if(result.hasTimeOfApplicabilityForPosition())
        tmpValue = result.getTimeOfApplicabilityForPosition()+1000;
      else if(result.hasTimeOfMessageReceptionOfPosition())
        tmpValue = result.getTimeOfMessageReceptionOfPosition()+1000;
    }
    else if(fsi == 0x2) {
      //减1秒
      if(result.hasTimeOfApplicabilityForPosition())
        tmpValue = result.getTimeOfApplicabilityForPosition()-1000;
      else if(result.hasTimeOfMessageReceptionOfPosition())
        tmpValue = result.getTimeOfMessageReceptionOfPosition()-1000;
    };
    return tmpValue;
  }

  private long parseTimeOfMessageReceptionOfVelocityHighPrecision() {
    double lsb = 1.00/Math.pow(2, 30);
    byte[] bytes = new byte[4];
    in.readBytes(bytes);
    //取fsi
    int fsi = bytes[0] >> 6 & 0x3;
    long tmpValue;
    //清除fsi标记
    bytes[0] = (byte)(bytes[0] & 0x3f);
    tmpValue = DataConvert.utcAm0IncSecondsToDateValue(bytes,lsb);

    if(fsi == 0x0){
      //不变
      if(result.hasTimeOfApplicabilityForVelocity())
        tmpValue = result.getTimeOfApplicabilityForVelocity();
      else if(result.hasTimeOfMessageReceptionForVelocity())
        tmpValue = result.getTimeOfMessageReceptionForVelocity();
    }
    else if(fsi == 0x1){
      //加1秒
      if(result.hasTimeOfApplicabilityForVelocity())
        tmpValue = result.getTimeOfApplicabilityForVelocity()+1000;
      else if(result.hasTimeOfMessageReceptionForVelocity())
        tmpValue = result.getTimeOfMessageReceptionForVelocity()+1000;
    }
    else if(fsi == 0x2) {
      //减1秒
      if(result.hasTimeOfApplicabilityForVelocity())
        tmpValue = result.getTimeOfApplicabilityForVelocity()-1000;
      else if(result.hasTimeOfMessageReceptionForVelocity())
        tmpValue = result.getTimeOfMessageReceptionForVelocity()-1000;
    }
    return tmpValue;
  }

  private float parseCalculatedAirSpeed() {
    byte[] bytes = new byte[2];
    in.readBytes(bytes);
    //取im
    int im = bytes[0] >> 7 & 0x1;

    //清除im标记
    bytes[0] = (byte)(bytes[0] & 0x7f);

    double lsb;
    if(im == 0) // 2的负数-14次方纳米/s
      lsb = 1.00/Math.pow(10, 9)/Math.pow(2, 14);
    else //马赫
       lsb = 0.001*340;
    float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
    return tmpValue;
  }

  private Cat021Proto.DiTrueAirspeed.Builder parseTrueAirspeed() {
    Cat021Proto.DiTrueAirspeed.Builder builder = Cat021Proto.DiTrueAirspeed.newBuilder();
    byte[] bytes = new byte[2];
    in.readBytes(bytes);
    //取re
    int re = bytes[0] >> 7 & 0x1;

    //清除re标记
    bytes[0] = (byte)(bytes[0] & 0x7f);
    float  tmpValue = (float)(DataConvert.toInt(bytes)*AdsbConstants.kNot2Second);

    if(re == 0) //值在数据表示的范围内
      builder.setIsTrue(true);
    else //值超出数据表示的范围
      builder.setIsTrue(false);

    builder.setValue(tmpValue);

    return builder;
  }
  private Cat021Proto.DiGeometricHeight.Builder parseDiGeometricHeight() {
    Cat021Proto.DiGeometricHeight.Builder builder = Cat021Proto.DiGeometricHeight.newBuilder();
    byte[] bytes = new byte[2];
    in.readBytes(bytes);
    double lsb = AdsbConstants.kFt2Metre;
    int  tmpValue = DataConvert.toComplementInt(bytes);

    if(tmpValue == 0x7fff) { //数据超出最大表示的范围
      builder.setIsTrue(false);
    }
    else {
      builder.setIsTrue(true);
      if(tmpValue < 10)
        lsb*=6.25;
    }
    builder.setValue((float)(tmpValue*lsb));
    return builder;
  }

  private Cat021Proto.DiQualityIndicators.Builder parseQualityIndicators() {
    Cat021Proto.DiQualityIndicators.Builder builder = Cat021Proto.DiQualityIndicators.newBuilder();
    int field = in.readByte() & 0xff;
    //处理主字段
    builder.setNucrOrNacv(field >> 5);
    builder.setNucpOrNic(field >> 1 & 0xf);
    boolean fxExistSub = (field  & 0x1) == 1;
    //没有第一个子字段就结束
    if(fxExistSub == false)
      return builder;
    //处理#1子字段
    Cat021Proto.DiQualityIndicatorsFirstSub.Builder firstSub = Cat021Proto.DiQualityIndicatorsFirstSub.newBuilder();
    field = in.readByte() & 0xff;
    firstSub.setBarometricAltitude(field >> 7 & 0x1);
    firstSub.setLevel(field >> 5 & 0xf);
    firstSub.setPosition(field >> 1 & 0xf);
    builder.setFirstSub(firstSub);

    return builder;
  }

  private Cat021Proto.DiMopsVersion.Builder parseMopsVersion() {
    Cat021Proto.DiMopsVersion.Builder builder = Cat021Proto.DiMopsVersion.newBuilder();
    int field = in.readByte() & 0xff;
    builder.setVersionIsSupported((field >> 6 & 0x1) == 0);
    builder.setVersionNumber(field >> 3 & 0x7);
    builder.setLinkTechnologyType(field & 0x7);
    return builder;
  }

  private int parseMode3aCodeInOctalRepresentation() {
    byte[] bytes = new byte[2];
    in.readBytes(bytes);
    bytes[0] = (byte)(bytes[0] & 0x0f);
    int  tmpValue = DataConvert.toInt(bytes);
    return tmpValue;
  }
  private float parseRollAngle() {
    byte[] data = new byte[2];
    final double lsb = 0.01;
    in.readBytes(data);

    float tmpValue = (float)(DataConvert.toComplementInt(data)*lsb);
    return tmpValue;
  }
  private float parseFlightLevel() {
    byte[] data = new byte[2];
    final double lsb = 0.25*AdsbConstants.kFt2Metre;
    in.readBytes(data);

    float tmpValue = (float)(DataConvert.toComplementInt(data)*lsb);
    return tmpValue;
  }
  private float parseMagneticHeading() {
    byte[] data = new byte[2];
    final double lsb = 360.00/Math.pow(2, 16);
    in.readBytes(data);

    float tmpValue = (float)(DataConvert.toInt(data)*lsb);
    return tmpValue;
  }

  private Cat021Proto.DiTargetStatus.Builder parseTargetStatus() {
    Cat021Proto.DiTargetStatus.Builder builder = Cat021Proto.DiTargetStatus.newBuilder();
    int field = in.readByte() & 0xff;
    builder.setPriorityStatus(field >> 2 & 0x7);
    builder.setSurveillanceStatus(field & 0x3);
    return builder;
  }

  private Cat021Proto.DiBarometricVerticalRate.Builder parseBarometricVerticalRate() {
    Cat021Proto.DiBarometricVerticalRate.Builder builder = Cat021Proto.DiBarometricVerticalRate.newBuilder();
    byte[] bytes = new byte[2];
    in.readBytes(bytes);
    //取re
    int re = bytes[0] >> 7 & 0x1;

    //清除re标记
    if((bytes[0] & 0x40) == 0x40) //负数
      bytes[0] = (byte)(bytes[0] | 0xc0);
    else
      bytes[0] = (byte)(bytes[0] & 0x7f);
    double lsb =  6.25 / 60 * AdsbConstants.kFt2Metre;
    float  tmpValue = (float)(DataConvert.toComplementInt(bytes)*lsb);

    if(re == 0) //值在数据表示的范围内
      builder.setIsTrue(true);
    else //值超出数据表示的范围
      builder.setIsTrue(false);

    builder.setValue(tmpValue);

    return builder;
  }

  private Cat021Proto.DiGeometricVerticalRate.Builder parseGeometricVerticalRate() {
    Cat021Proto.DiGeometricVerticalRate.Builder builder = Cat021Proto.DiGeometricVerticalRate.newBuilder();
    byte[] bytes = new byte[2];
    in.readBytes(bytes);
    //取re
    int re = bytes[0] >> 7 & 0x1;

    //清除re标记
    if((bytes[0] & 0x40) == 0x40) //负数
      bytes[0] = (byte)(bytes[0] | 0xc0);
    else
      bytes[0] = (byte)(bytes[0] & 0x7f);
    double lsb =  6.25 / 60 * AdsbConstants.kFt2Metre;
    float  tmpValue = (float)(DataConvert.toComplementInt(bytes)*lsb);

    if(re == 0) //值在数据表示的范围内
      builder.setIsTrue(true);
    else //值超出数据表示的范围
      builder.setIsTrue(false);

    builder.setValue(tmpValue);

    return builder;
  }

  private Cat021Proto.DiGroundVector.Builder parseGroundVector() {
    Cat021Proto.DiGroundVector.Builder builder = Cat021Proto.DiGroundVector.newBuilder();
    //ground Speed
    byte[] bytes = new byte[2];
    in.readBytes(bytes);
    //取re
    int re = bytes[0] >> 7 & 0x1;

    //清除re标记
    bytes[0] = (byte)(bytes[0] & 0x7f);
    double lsb =  1.00/Math.pow(10, 9)/Math.pow(2, 14);
    float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);

    if(re == 0)
      builder.setGroundSpeedIsTrue(true);
    else
      builder.setGroundSpeedIsTrue(false);

    builder.setGroundSpeed(tmpValue);

    //Track Angle
    in.readBytes(bytes);
    lsb = 360.00/Math.pow(2, 16);
    tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
    builder.setTrackAngle(tmpValue);

    return builder;
  }

  private Cat021Proto.DiTrackAngleRate.Builder parseTrackAngleRate() {
    Cat021Proto.DiTrackAngleRate.Builder builder = Cat021Proto.DiTrackAngleRate.newBuilder();
    byte[] bytes = new byte[2];
    in.readBytes(bytes);

    //bits-16/11 Spare bits set to zero
    if((bytes[0] & 0x2) == 0x2) //负数
      bytes[0] = (byte)(bytes[0] | 0xfc);
    else
      bytes[0] = (byte)(bytes[0] & 0x3f);
    double lsb =  1.00/32;
    int bytesValue = DataConvert.toComplementInt(bytes);
    float  tmpValue = (float)(bytesValue*lsb);

    if(bytesValue < 512) //值在数据表示的范围内
      builder.setIsTrue(true);
    else //值超出数据表示的范围
      builder.setIsTrue(false);

    builder.setValue(tmpValue);

    return builder;
  }
  private Cat021Proto.DiMetInformation.Builder parseMetInformation() {
    Cat021Proto.DiMetInformation.Builder builder = Cat021Proto.DiMetInformation.newBuilder();
    int field = in.readByte() & 0xff;
    //处理主字段
    boolean hasWindSpeed = (field  & 0x80) == 0x80;
    boolean hasWindDirection = (field  & 0x40) == 0x40;
    boolean hasTemperature = (field  & 0x20) == 0x20;
    boolean hasTurbulence = (field  & 0x10) == 0x10;

    boolean fxExistSub = (field  & 0x1) == 1;
    //没有子字段就结束
    if(fxExistSub == false)
      return null;

    //风速
    if(hasWindSpeed) {
      byte[] bytes = new byte[2];
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*AdsbConstants.kNot2Second);
      builder.setWindSpeed(tmpValue);
    }
    //风向
    if(hasWindDirection) {
      byte[] bytes = new byte[2];
      in.readBytes(bytes);
      int  tmpValue = DataConvert.toInt(bytes);
      builder.setWindDirection(tmpValue);
    }
    //温度
    if(hasTemperature) {
      double lsb = 0.25;
      byte[] bytes = new byte[2];
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toComplementInt(bytes)*lsb);
      builder.setTemperature(tmpValue);
    }
    //温度
    if(hasTurbulence) {
      byte tmpValue = in.readByte();
      builder.setTurbulence(tmpValue & 0xff);
    }
    return builder;
  }

  private Cat021Proto.DiIntermediateStateSelectedAltitude.Builder parseIntermediateStateSelectedAltitude() {
    Cat021Proto.DiIntermediateStateSelectedAltitude.Builder builder = Cat021Proto.DiIntermediateStateSelectedAltitude.newBuilder();
    byte[] bytes = new byte[2];
    in.readBytes(bytes);

    builder.setSourceAvailability((bytes[0] & 0x80) == 0x80);
    builder.setSource(bytes[0] >> 5 & 0x3);

    if((bytes[0] & 0x10) == 0x10) //负数
      bytes[0] = (byte)(bytes[0] | 0xe0);
    else
      bytes[0] = (byte)(bytes[0] & 0x1f);
    double lsb =  25.00*AdsbConstants.kFt2Metre;
    float  tmpValue = (float)(DataConvert.toComplementInt(bytes)*lsb);

    builder.setAltitude(tmpValue);

    return builder;
  }

  private Cat021Proto.DiFinalStateSelectedAltitude.Builder parseFinalStateSelectedAltitude() {
    Cat021Proto.DiFinalStateSelectedAltitude.Builder builder = Cat021Proto.DiFinalStateSelectedAltitude.newBuilder();
    byte[] bytes = new byte[2];
    in.readBytes(bytes);

    builder.setManageVerticalModeActive((bytes[0] & 0x80) == 0x80);
    builder.setAltitudeHoldModeActive((bytes[0] & 0x40) == 0x40);
    builder.setApproachModeActive((bytes[0] & 0x20) == 0x20);

    if((bytes[0] & 0x10) == 0x10) //负数
      bytes[0] = (byte)(bytes[0] | 0xe0);
    else
      bytes[0] = (byte)(bytes[0] & 0x1f);
    double lsb =  25.00*AdsbConstants.kFt2Metre;
    float  tmpValue = (float)(DataConvert.toComplementInt(bytes)*lsb);

    builder.setAltitude(tmpValue);

    return builder;
  }

  private Cat021Proto.DiTrajectoryIntent.Builder parseTrajectoryIntent() {
    Cat021Proto.DiTrajectoryIntent.Builder builder = Cat021Proto.DiTrajectoryIntent.newBuilder();
    int field = in.readByte() & 0xff;
    //处理主字段
    boolean hasFirstSub = (field  & 0x80) == 0x80;
    boolean hasSecondSub = (field  & 0x40) == 0x40;
    boolean sub = (field  & 0x1) == 1;
    //没有子字段就结束
    if(sub == false)
      return null;

    //处理#1子字段
    if(hasFirstSub) {
      field = in.readByte() & 0xff;
      builder.setNavAvailable((field  & 0x80) == 0);
      builder.setNvbAvailable((field & 0x40) == 0);
      sub = (field  & 0x1) == 1;
      //没有2子字段就结束
      if(sub == false)
        return null;
    }
    //处理#2子字段
    if(hasSecondSub) {
      //循环因子
      builder.setRepetitionFactor(in.readByte() & 0xff);
      int rf = builder.getRepetitionFactor();
      while(rf>0){
        Cat021Proto.DiTrajectoryIntentSecondSub.Builder subBuilder = Cat021Proto.DiTrajectoryIntentSecondSub.newBuilder();
        //tcp
        field = in.readByte() & 0xff;
        subBuilder.setTcpNumberAvailable((field & 0x80) == 0);
        subBuilder.setTcpCompliance((field & 0x40) == 0);
        subBuilder.setTcpNumber((field  & 0x3f));
        //海拔高度
        byte[] twoDataBytes = new byte[2];
        in.readBytes(twoDataBytes);
        double lsb =  10*AdsbConstants.kFt2Metre;
        float  tmpValue = (float)(DataConvert.toComplementInt(twoDataBytes)*lsb);
        subBuilder.setAltitude(tmpValue);
        //维度
        byte[] threeDataBytes = new byte[3];
        in.readBytes(threeDataBytes);
        lsb = 180.00/Math.pow(2, 23);
        tmpValue = (float)(DataConvert.toComplementInt(threeDataBytes)*lsb);
        subBuilder.setLatitude(tmpValue);
        //经度
        in.readBytes(threeDataBytes);
        lsb = 180.00/Math.pow(2,23);
        tmpValue = (float)(DataConvert.toComplementInt(threeDataBytes)*lsb);
        subBuilder.setLongitude(tmpValue);
        //Point Type,TD,TRA,TOA
        field = in.readByte() & 0xff;
        subBuilder.setPointType(field >> 4 & 0xf);
        subBuilder.setTd(field >> 2 & 0x3);
        subBuilder.setTurnRadiusAvailabilty((field & 0x2) == 0x2);
        subBuilder.setToaAvailable((field & 0x1) == 0);
        //TOV
        lsb = 1.00;
        subBuilder.setTov(internalRead3BytesDateValue(lsb));
        //TTR
        in.readBytes(twoDataBytes);
        lsb = 0.01/Math.pow(10, 9);
        tmpValue = (float)(DataConvert.toInt(twoDataBytes)*lsb);
        subBuilder.setTtr(tmpValue);
        builder.addSub(subBuilder);

        --rf;
      }
    }
    return builder;
  }

  private Cat021Proto.DiServiceManagement.Builder parseServiceManagement() {
    Cat021Proto.DiServiceManagement.Builder builder = Cat021Proto.DiServiceManagement.newBuilder();
    double lsb = 0.5;
    int tmpValue =  in.readByte() & 0xff;

    if(tmpValue != 0xff) //值在数据表示的范围内
      builder.setIsTrue(false);
    else //值超出数据表示的范围
      builder.setIsTrue(true);

    builder.setValue((float)(tmpValue*lsb));
    return builder;
  }

  private Cat021Proto.DiAircraftOperationalStatus.Builder parseAircraftOperationalStatus() {
    Cat021Proto.DiAircraftOperationalStatus.Builder builder = Cat021Proto.DiAircraftOperationalStatus.newBuilder();
    int field =  in.readByte() & 0xff;
    builder.setTcsResolutionAdvisoryActive((field & 0x80) == 0x80);
    builder.setTargetChangeReportCapability(field >> 5 & 0x3);
    builder.setTargetStateReportCapability((field & 0x10) == 0x10);

    builder.setAirReferencedVelocityReportCapability((field & 0x8) == 0x8);
    builder.setCockpitDisplayOfTrafficInformationAirborneOperational((field & 0x4) == 0x4);
    builder.setTcsSystemStatusOperational((field & 0x2) == 0);
    return builder;
  }

  private Cat021Proto.DiSurfaceCapabilitiesAndCharacteristics.Builder parseSurfaceCapabilitiesAndCharacteristics() {
    Cat021Proto.DiSurfaceCapabilitiesAndCharacteristics.Builder builder = Cat021Proto.DiSurfaceCapabilitiesAndCharacteristics.newBuilder();
    int field =  in.readByte() & 0xff;
    builder.setPositionOffsetAppliedReference((field & 0x20) == 0x20);
    builder.setCockpitDisplayOfTrafficInformationSurfaceOperational((field & 0x10) == 0x10);
    builder.setClassB2TransmitPowerLessThan70Watts((field & 0x8) == 0x8);
    builder.setReceivingAtcServices((field & 0x4) == 0x4);
    builder.setSettingOfSwitch((field & 0x2) == 0x2);
    //没有子字段就结束
    if((field  & 0x1) == 0)
      return builder;

    field =  in.readByte() & 0x0f;
    builder.setLengthWidthOfAircraft(field);

    return builder;
  }

  private Cat021Proto.DiModeSmbData.Builder parseModeSmbData() {
    Cat021Proto.DiModeSmbData.Builder builder = Cat021Proto.DiModeSmbData.newBuilder();
    int field;
    //循环因子
    builder.setRepetitionFactor(in.readByte() & 0xff);
    int rf = builder.getRepetitionFactor();
    while(rf>0){
      Cat021Proto.DiModeSmbDataBds.Builder bdsBuilder = Cat021Proto.DiModeSmbDataBds.newBuilder();
      byte[] bytes = new byte[7];
      in.readBytes(bytes);
      bdsBuilder.setCommbMessageData(DataConvert.toLong(bytes));
      field = in.readByte();
      bdsBuilder.setStoreAddress1(field>>4 & 0xf);
      bdsBuilder.setStoreAddress2(field & 0xf);

      builder.addBds(bdsBuilder);

      --rf;
    }
    return builder;
  }

  private Cat021Proto.DiDataAges.Builder parseDataAges() {
    Cat021Proto.DiDataAges.Builder builder = Cat021Proto.DiDataAges.newBuilder();
    boolean hasAos = false;
    boolean hasTrd = false;
    boolean hasM3a = false;
    boolean hasQi = false;
    boolean hasTi = false;
    boolean hasMam = false;
    boolean hasGh = false;
    boolean hasFl = false;
    boolean hasIsa = false;
    boolean hasFsa = false;
    boolean hasAs = false;
    boolean hasTas = false;
    boolean hasMh = false;
    boolean hasBvr = false;
    boolean hasGvr = false;
    boolean hasGv = false;
    boolean hasTar = false;
    boolean hasTid = false;
    boolean hasTs = false;
    boolean hasMet = false;
    boolean hasRoa = false;
    boolean hasAra = false;
    boolean hasScc = false;

    int field = in.readByte() & 0xff;
    //处理第1个主字段
    hasAos = (field  & 0x80) == 0x80;
    hasTrd = (field  & 0x40) == 0x40;
    hasM3a = (field  & 0x20) == 0x20;
    hasQi = (field  & 0x10) == 0x10;
    hasTi = (field  & 0x8) == 0x8;
    hasMam = (field  & 0x4) == 0x4;
    hasGh = (field  & 0x2) == 0x2;

    //处理第2个主字段
    if((field  & 0x1) == 1) {
      field = in.readByte() & 0xff;
      hasFl = (field  & 0x80) == 0x80;
      hasIsa = (field  & 0x40) == 0x40;
      hasFsa = (field  & 0x20) == 0x20;
      hasAs = (field  & 0x10) == 0x10;
      hasTas = (field  & 0x8) == 0x8;
      hasMh = (field  & 0x4) == 0x4;
      hasBvr = (field  & 0x2) == 0x2;
      //处理第3个主字段
      if((field  & 0x1) == 1) {
        field = in.readByte() & 0xff;
        hasGvr = (field  & 0x80) == 0x80;
        hasGv = (field  & 0x40) == 0x40;
        hasTar = (field  & 0x20) == 0x20;
        hasTid = (field  & 0x10) == 0x10;
        hasTs = (field  & 0x8) == 0x8;
        hasMet = (field  & 0x4) == 0x4;
        hasRoa = (field  & 0x2) == 0x2;
        //处理第4个主字段
        if((field  & 0x1) == 1) {
          field = in.readByte() & 0xff;
          hasAra = (field  & 0x80) == 0x80;
          hasScc = (field  & 0x40) == 0x40;
        }
      }
    }

    double lsb = 0.1;
    byte[] bytes = new byte[1];
    if(hasAos) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setAos(tmpValue);
    }
    if(hasTrd) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setTrd(tmpValue);
    }
    if(hasM3a) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setM3A(tmpValue);
    }
    if(hasQi) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setQi(tmpValue);
    }
    if(hasTi) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setTi(tmpValue);
    }
    if(hasMam) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setMam(tmpValue);
    }
    if(hasGh) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setGh(tmpValue);
    }
    if(hasFl) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setFl(tmpValue);
    }
    if(hasIsa) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setIsa(tmpValue);
    }
    if(hasFsa) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setFsa(tmpValue);
    }
    if(hasAs) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setAs(tmpValue);
    }
    if(hasTas) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setTas(tmpValue);
    }
    if(hasMh) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setMh(tmpValue);
    }
    if(hasBvr) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setBvr(tmpValue);
    }
    if(hasGvr) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setGvr(tmpValue);
    }
    if(hasGv) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setGv(tmpValue);
    }
    if(hasTar) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setTar(tmpValue);
    }
    if(hasTid) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setTid(tmpValue);
    }
    if(hasTs) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setTs(tmpValue);
    }
    if(hasMet) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setMet(tmpValue);
    }
    if(hasRoa) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setRoa(tmpValue);
    }
    if(hasAra) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setAra(tmpValue);
    }
    if(hasScc) {
      in.readBytes(bytes);
      float  tmpValue = (float)(DataConvert.toInt(bytes)*lsb);
      builder.setScc(tmpValue);
    }

    return builder;
  }
}
