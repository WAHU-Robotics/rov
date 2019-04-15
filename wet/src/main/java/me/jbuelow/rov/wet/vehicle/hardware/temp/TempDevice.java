package me.jbuelow.rov.wet.vehicle.hardware.temp;

import me.jbuelow.rov.wet.vehicle.hardware.I2CDevice;

import java.io.IOException;

public interface TempDevice extends I2CDevice {

  float getTemp() throws IOException;
}
