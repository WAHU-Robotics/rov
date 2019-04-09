package me.jbuelow.rov.wet.vehicle.hardware;

import java.io.IOException;

public interface PwmDevice {

	int getBus();

	int getAddress();

	PwmChannel getChannel(int channel);

	void setPWMFreqency(double frequency) throws IOException;

	void setAllPWM(int on, int off) throws IOException;
}
