package me.jbuelow.rov.wet.vehicle.hardware;

import java.io.IOException;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("noHardware")
public class MockPwmInterface implements PwmDevice {

	@Override
	public int getBus() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAddress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PwmChannel getChannel(int channel) {
		// TODO Auto-generated method stub
		return new PwmChannel() {
			
			@Override
			public void setServoPulse(float pulseMS) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setPWM(int on, int off) throws IOException {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public void setPWMFreqency(double frequency) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAllPWM(int on, int off) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
