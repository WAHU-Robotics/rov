package me.jbuelow.rov.wet.vehicle.hardware.pwm;

import me.jbuelow.rov.wet.vehicle.hardware.I2CDevice;

import java.io.IOException;

public interface PwmDevice extends I2CDevice {

	PwmChannel getChannel(int channel);

	void setPWMFreqency(double frequency) throws IOException;

	void setAllPWM(int on, int off) throws IOException;
}
