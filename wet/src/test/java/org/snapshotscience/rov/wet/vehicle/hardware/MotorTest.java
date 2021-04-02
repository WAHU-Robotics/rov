package org.snapshotscience.rov.wet.vehicle.hardware;

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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import org.snapshotscience.rov.wet.vehicle.hardware.i2c.pwm.Motor;
import org.snapshotscience.rov.wet.vehicle.hardware.i2c.pwm.PwmChannel;
import org.junit.Before;
import org.junit.Test;

public class MotorTest {
  
  private PwmChannel pwmChannel;
  private Motor motor;
  
  @Before
  public void setup() throws IOException {
    pwmChannel = mock(PwmChannel.class);
    motor = new Motor(pwmChannel);
  }
  @Test
  public void testSetPower() throws IOException {
    motor.setPower(0);
    
    verify(pwmChannel, times(1)).setServoPulse(1.5f);
    
    reset(pwmChannel);

    motor.setPower(0);
    verify(pwmChannel, times(0)).setServoPulse(1.5f);

    motor.setPower(1000);
    verify(pwmChannel, times(1)).setServoPulse(2.0f);

    reset(pwmChannel);

    motor.setPower(-1000);
    verify(pwmChannel, times(1)).setServoPulse(1.0f);
}

  @Test
  public void testGetPower() throws IOException {
    assertThat(motor.getPower(), is(nullValue()));
    
    motor.arm();
    
    assertThat(motor.getPower(), is(0));
  }

  @Test
  public void testIsArmed() {
    assertThat(motor.isArmed(), is(false));
  }

  @Test
  public void testArm() throws IOException {
    motor.arm();
    assertThat(motor.isArmed(), is(false));
  }
}
