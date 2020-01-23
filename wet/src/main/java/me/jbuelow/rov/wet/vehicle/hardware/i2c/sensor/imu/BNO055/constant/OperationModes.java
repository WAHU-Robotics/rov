package me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.imu.BNO055.constant;

public abstract class OperationModes {
  public static byte CONFIG_MODE = 0x0;
  public static byte ACCONLY = 0x1;
  public static byte MAGONLY = 0x2;
  public static byte GYROONLY = 0x3;
  public static byte ACCMAG = 0x4;
  public static byte ACCGYRO = 0x5;
  public static byte MAGGYRO = 0x6;
  public static byte AMG = 0x7;

  public static byte IMU = 0x8;
  public static byte COMPASS = 0x9;
  public static byte M4G = 0xA;
  public static byte NDOF_FMC_OFF = 0xB;
  public static byte NDOF = 0xC;
}
