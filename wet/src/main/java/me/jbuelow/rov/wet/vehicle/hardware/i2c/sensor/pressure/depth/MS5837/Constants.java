package me.jbuelow.rov.wet.vehicle.hardware.i2c.sensor.pressure.depth.MS5837;

public class Constants {

  public static int DEFAULT_ADDRESS = 0x76;

  public static byte RESET = 0x1E;
  public static byte ADC_READ = 0x00;
  public static byte PROM_READ_START = (byte) 0xA0;
  public static byte CONVERT_D1_256 = 0x40;
  public static byte CONVERT_D1_512 = 0x42;
  public static byte CONVERT_D1_1024 = 0x44;
  public static byte CONVERT_D1_2048 = 0x46;
  public static byte CONVERT_D1_4096 = 0x48;
  public static byte CONVERT_D1_8192 = 0x4A;
  public static byte CONVERT_D2_256 = 0x50;
  public static byte CONVERT_D2_512 = 0x52;
  public static byte CONVERT_D2_1024 = 0x54;
  public static byte CONVERT_D2_2048 = 0x56;
  public static byte CONVERT_D2_4096 = 0x58;
  public static byte CONVERT_D2_8192 = 0x5A;

}
