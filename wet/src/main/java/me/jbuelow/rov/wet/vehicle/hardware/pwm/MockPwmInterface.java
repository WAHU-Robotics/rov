package me.jbuelow.rov.wet.vehicle.hardware.pwm;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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
			public void setServoPulse(float pulseMS) {
			}

			@Override
			public void setPWM(int on, int off) {
			}

			@Override
			public int getChannelNumber() {
				return 0;
			}
		};
	}

	@Override
	public void setPWMFreqency(double frequency) {
	}

	@Override
	public void setAllPWM(int on, int off) {
	}
}
