package me.jbuelow.rov.wet.vehicle.hardware.pwm;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Profile("noHardware")
public class MockPwmInterface implements PwmDevice {

	@Override
	public int getBus() {
		return 0;
	}

	@Override
	public int getAddress() {
		return 0;
	}

	@Override
	public PwmChannel getChannel(int channel) {
		return new PwmChannel() {
			
			@Override
			public void setServoPulse(float pulseMS) throws IOException {}
			
			@Override
			public void setPWM(int on, int off) throws IOException {}

			@Override
			public int getChannelNumber() {
				return 0;
			}
		};
	}

	@Override
	public void setPWMFreqency(double frequency) throws IOException {}

	@Override
	public void setAllPWM(int on, int off) throws IOException {}
}
