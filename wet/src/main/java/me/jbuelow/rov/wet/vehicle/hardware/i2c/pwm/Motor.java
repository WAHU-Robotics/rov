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
import lombok.extern.slf4j.Slf4j;

/**
 * Object used to represent a physical motor.
 */
@Slf4j
public class Motor {
  private static final float POWER_TO_PULSE_FACTOR = 2000f;
  private static final float CENTER_PULSE = 1.5f;

  private boolean armed = false;
  private PwmChannel pwmChannel;
  private Integer power;

  public Motor(PwmChannel pwmChannel) throws IOException {
    this.pwmChannel = pwmChannel;
  }
  
  public void setPower(int power) throws IOException {
    if (this.power == null || !this.power.equals(power)) {
      this.power = power;
      pwmChannel.setServoPulse(convertToPulse(power));
    }
  }
  
  public Integer getPower() {
    return power;
  }
  
  public boolean isArmed() {
    return armed;
  }
  
  public void arm() throws IOException {
    if (!armed) {
      //arm our Motors
      log.debug("Arming motor on channel " + pwmChannel.getChannelNumber());
      pwmChannel.setServoPulse(1.5f);
      delay(1500);
      setPower(0);
      log.debug("Arming complete.");
    }
  }

  private float convertToPulse(int power) {
    return ((float) power / POWER_TO_PULSE_FACTOR) + CENTER_PULSE;
  }

  private void delay(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      //
    }
  }
}
