package me.jbuelow.rov.wet.vehicle.hardware.i2c.pwm;

/* This file is part of WAHU ROV Software.
 *
 * WAHU ROV Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WAHU ROV Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WAHU ROV Software.  If not, see <https://www.gnu.org/licenses/>.
 */

import java.io.IOException;
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
      public void setServoPulse(float pulseMS) throws IOException {
      }
			
			@Override
      public void setPWM(int on, int off) throws IOException {
      }

			@Override
			public int getChannelNumber() {
				return 0;
			}
		};
	}

	@Override
  public void setPWMFreqency(double frequency) throws IOException {
  }

	@Override
  public void setAllPWM(int on, int off) throws IOException {
  }
}
