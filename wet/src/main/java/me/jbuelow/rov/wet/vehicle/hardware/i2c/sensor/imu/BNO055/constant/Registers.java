package me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.imu.BNO055.constant;

/**
 * Labeled register addresses taken from BNO055 datasheet
 */
public abstract class Registers {
  //Page 1
  public static int MAG_RADIUS_MSB = 0x6A;
  public static int MAG_RADIUS_LSB = 0x69;
  public static int ACC_RADIUS_MSB = 0x68;
  public static int ACC_RADIUS_LSB = 0x67;
  public static int GYR_OFFSET_Z_MSB = 0x66;
  public static int GYR_OFFSET_Z_LSB = 0x65;
  public static int GYR_OFFSET_Y_LSB = 0x63;
  public static int GYR_OFFSET_Y_MSB = 0x64;
  public static int GYR_OFFSET_X_MSB = 0x62;
  public static int GYR_OFFSET_X_LSB = 0x61;
  public static int MAG_OFFSET_Z_MSB = 0x60;
  public static int MAG_OFFSET_Z_LSB = 0x5F;
  public static int MAG_OFFSET_Y_MSB = 0x5E;
  public static int MAG_OFFSET_Y_LSB = 0x5D;
  public static int MAG_OFFSET_X_MSB = 0x5C;
  public static int MAG_OFFSET_X_LSB = 0x5B;
  public static int ACC_OFFSET_Z_MSB = 0x5A;
  public static int ACC_OFFSET_Z_LSB = 0x59;
  public static int ACC_OFFSET_Y_MSB = 0x58;
  public static int ACC_OFFSET_Y_LSB = 0x57;
  public static int ACC_OFFSET_X_MSB = 0x56;
  public static int ACC_OFFSET_X_LSB = 0x55;

  public static int AXIS_MAP_SIGN = 0x42;
  public static int AXIS_MAP_CONFIG = 0x41;
  public static int TEMP_SOURCE = 0x40;
  public static int SYS_TRIGGER = 0x3F;
  public static int PWR_MODE = 0x3E;
  public static int OPR_MODE = 0x3D;

  public static int UNIT_SEL = 0x3B;
  public static int SYS_ERR = 0x3A;
  public static int SYS_STATUS = 0x39;
  public static int SYS_CLK_STATUS = 0x38;
  public static int INT_STA = 0x37;
  public static int ST_RESULT = 0x36;
  public static int CALIB_STAT = 0x35;
  public static int TEMP = 0x34;
  public static int GRV_Data_Z_MSB = 0x33;
  public static int GRV_Data_Z_LSB = 0x32;
  public static int GRV_Data_Y_MSB = 0x31;
  public static int GRV_Data_Y_LSB = 0x30;
  public static int GRV_Data_X_MSB = 0x2F;
  public static int GRV_Data_X_LSB = 0x2E;
  public static int LIA_Data_Z_MSB = 0x2D;
  public static int LIA_Data_Z_LSB = 0x2C;
  public static int LIA_Data_Y_MSB = 0x2B;
  public static int LIA_Data_Y_LSB = 0x2A;
  public static int LIA_Data_X_MSB = 0x29;
  public static int LIA_Data_X_LSB = 0x28;
  public static int QUA_Data_z_MSB = 0x27;
  public static int QUA_Data_z_LSB = 0x26;
  public static int QUA_Data_y_MSB = 0x25;
  public static int QUA_Data_y_LSB = 0x24;
  public static int QUA_Data_x_MSB = 0x23;
  public static int QUA_Data_x_LSB = 0x22;
  public static int QUA_Data_w_MSB = 0x21;
  public static int QUA_Data_w_LSB = 0x20;
  public static int EUL_Pitch_MSB = 0x1F;
  public static int EUL_Pitch_LSB = 0x1E;
  public static int EUL_Roll_MSB = 0x1D;
  public static int EUL_Roll_LSB = 0x1C;
  public static int EUL_Heading_MSB = 0x1B;
  public static int EUL_Heading_LSB = 0x1A;
  public static int GYR_DATA_Z_MSB = 0x19;
  public static int GYR_DATA_Z_LSB = 0x18;
  public static int GYR_DATA_Y_MSB = 0x17;
  public static int GYR_DATA_Y_LSB = 0x16;
  public static int GYR_DATA_X_MSB = 0x15;
  public static int GYR_DATA_X_LSB = 0x14;
  public static int MAG_DATA_Z_MSB = 0x13;
  public static int MAG_DATA_Z_LSB = 0x12;
  public static int MAG_DATA_Y_MSB = 0x11;
  public static int MAG_DATA_Y_LSB = 0x10;
  public static int MAG_DATA_X_MSB = 0xF;
  public static int MAG_DATA_X_LSB = 0xE;
  public static int ACC_DATA_Z_MSB = 0xD;
  public static int ACC_DATA_Z_LSB = 0xC;
  public static int ACC_DATA_Y_MSB = 0xB;
  public static int ACC_DATA_Y_LSB = 0xA;
  public static int ACC_DATA_X_MSB = 0x9;
  public static int ACC_DATA_X_LSB = 0x8;
  public static int PAGE_ID = 0x7;
  public static int BL_Rev_ID = 0x6;
  public static int SW_REV_ID_MSB = 0x5;
  public static int SW_REV_ID_LSB = 0x4;
  public static int GYR_ID = 0x3;
  public static int MAG_ID = 0x2;
  public static int ACC_ID = 0x1;
  public static int CHIP_ID = 0x0;

  //Page 2
  public static int UNIQUE_ID = 0x5F; //Start of 16 byte range
  public static int GYR_AM_SET = 0x1F;
  public static int GYR_AM_THRES = 0x1E;
  public static int GYR_DUR_Z = 0x1D;
  public static int GYR_HR_Z_SET = 0x1C;
  public static int GYR_DUR_Y = 0x1B;
  public static int GYR_HR_Y_SET = 0x1A;
  public static int GYR_DUR_X = 0x19;
  public static int GYR_HR_X_SET = 0x18;
  public static int GYR_INT_SETING = 0x17;
  public static int ACC_NM_SET = 0x16;
  public static int ACC_NM_THRE = 0x15;
  public static int ACC_HG_THRES = 0x14;
  public static int ACC_HG_DURATION = 0x13;
  public static int ACC_INT_Settings = 0x12;
  public static int ACC_AM_THRES = 0x11;
  public static int INT_EN = 0x10;
  public static int INT_MSK = 0xF;

  public static int GYR_Sleep_Config = 0xD;
  public static int ACC_Sleep_Config = 0xC;
  public static int GYR_Config_1 = 0xB;
  public static int GYR_Config_0 = 0xA;
  public static int MAG_Config = 0x9;
  public static int ACC_Config = 0x8;
}
