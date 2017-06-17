package com.pekall.fms.collection.cat021_bak;

/**
 * Created by maxl on 15-9-21.
 */
public class AdsbConstants {
  //cat=021|len|FSPEC|第一个记录数据项目|...|FSPEC|最后一个记录数据项目
  public static final byte CAT_FLAG = 21;
  public static final int  ADSB_IN_HEAD_LENGTH = 3;

  //3:ADSB_IN_HEAD_LENGTH
  //2:数据源识别
  //3+2
  public static final int  ADSB_IN_MIN_LENGTH = 5;

  public static final int  ADSB_FSPEC_LENGTH = 2;

  //Data Item

  //Data Source Identification 必须的字段
  public static final short DI_DATA_SOURCE_IDENTIFICATION = 10;
  //Target Report Descriptor
  public static final short DI_TARGET_REPORT_DESCRIPTOR = 40;
  //Target ID
  public static final short DI_TRACK_ID = 161;
  //Service Identification
  public static final short DI_SERVER_IDENTIFICATION = 15;
  //Time of Applicability for Position
  public static final short DI_TIME_OF_APPLICABILITY_FOR_POSITION = 71;
  //Position in WGS-84 co-ordinates
  public static final short DI_POSITION_IN_WGS84 = 130;
  //Position in WGS-84 co-ordinates, high res.
  public static final short DI_POSITION_IN_WGS84_HIGH = 131;
  //Time of Applicability for Velocity
  public static final short DI_TIME_OF_APPLICABILITY_FOR_VELOCITY = 72;
  //Air Speed
  public static final short DI_AIR_SPEED = 150;
  //True Air Speed
  public static final short DI_TRUE_AIR_SPEED = 151;
  //Target Address
  public static final short DI_TARGET_ADDRESS = 80;
  //Time of Message Reception of Position
  public static final short DI_TIME_OF_MESSAGE_RECEPTION_OF_POSITION = 73;
  //Time of Message Reception of Position-High-Precision
  public static final short DI_TIME_OF_MESSAGE_RECEPTION_OF_POSITION_HIGH_PRECISION = 74;
  //Time of Message Reception of Velocity
  public static final short DI_TIME_OF_MESSAGE_RECEPTION_OF_VELOCITY = 75;
  //Time of Message Reception of Velocity-High-Precision
  public static final short DI_TIME_OF_MESSAGE_RECEPTION_OF_VELOCITY_HIGH_PRECISION = 76;
  //Geometric Altitude
  public static final short DI_GEOMETRIC_ALTITUDE = 140;
  //Quality Indicators
  public static final short DI_QUALITY_INDICATORS = 90;
  //MOPS Version
  public static final short DI_MOPS_VERSION = 210;
  //Mode 3/A Code
  public static final short DI_MODE_3A_CODE = 70;
  //Roll Angle
  public static final short DI_ROLL_ANGLE = 230;
  //Flight Level
  public static final short DI_FLIGHT_LEVEL = 145;
  //Magnetic Heading
  public static final short DI_MAGNETIC_HEADING = 152;
  //Target Status
  public static final short DI_TARGET_STATUS = 200;
  //Barometric Vertical Rate
  public static final short DI_BAROMETRIC_VERTICAL_RATE = 155;
  //Geometric Vertical Rate
  public static final short DI_GEOMETRIC_VERTICAL_RATE = 157;
  //Ground Vector
  public static final short DI_GROUND_VECTOR = 160;
  //Track Angle Rate
  public static final short DI_TRACK_ANGLE_RATE = 165;
  //Time of Report Transmission
  public static final short DI_TIME_OF_REPORT_TRANSMISSION = 77;
  //Target Identification
  public static final short DI_TARGET_IDENTIFICATION = 170;
  //Emitter Category
  public static final short DI_EMITTER_CATEGORY = 20;
  //Met Information
  public static final short DI_MET_INFORMATION = 220;
  //Intermediate State Selected Altitude
  public static final short DI_INTERMEDIATE_STATE_SELECTED_ALTITUDE = 146;
  //Final State Selected Altitude
  public static final short DI_FINAL_STATE_SELECTED_ALTITUDE = 148;
  //Trajectory Intent
  public static final short DI_TRAJECTORY_INTENT = 110;
  //Service Management
  public static final short DI_SERVICE_MANAGEMENT = 16;
  //Aircraft Operational Status
  public static final short DI_AIRCRAFT_OPERATIONAL_STATUS = 8;
  //Surface Capabilities and Characteristics
  public static final short DI_SURFACE_CAPABILITIES_AND_CHARACTERISTICS = 271;
  //Message Amplitude
  public static final short DI_MESSAGE_AMPLITUDE = 132;
  //Mode S MB Data
  public static final short DI_MODE_S_MB_DATA = 250;
  //ACAS Resolution Advisory Report
  public static final short DI_ACAS_RESOLUTION_ADVISORY_REPORT = 260;
  //Receiver ID
  public static final short DI_RECEIVER_ID = 400;
  //Data Ages
  public static final short DI_DATA_AGES = 295;

  //RE Reserved Expansion Field
  //SP Special Purpose Field





}
