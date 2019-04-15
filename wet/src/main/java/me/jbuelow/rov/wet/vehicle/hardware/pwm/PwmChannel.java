package me.jbuelow.rov.wet.vehicle.hardware.pwm;

import java.io.IOException;

public interface PwmChannel {
	
	int getChannelNumber();

	void setPWM(int on, int off) throws IOException;

	void setServoPulse(float pulseMS) throws IOException;
}
