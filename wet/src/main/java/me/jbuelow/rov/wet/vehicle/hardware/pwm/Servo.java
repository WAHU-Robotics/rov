package me.jbuelow.rov.wet.vehicle.hardware.pwm;

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
public class Servo {
  private static final float VALUE_TO_PULSE_FACTOR = 2000f;
  private static final float CENTER_PULSE = 1.5f;

  private PwmChannel pwmChannel;
  private Integer value;

  public Servo(PwmChannel pwmChannel) throws IOException {
    this.pwmChannel = pwmChannel;
  }
  
  public void setValue(int value) throws IOException {
    if (this.value == null || !this.value.equals(value)) {
      this.value = value;
      pwmChannel.setServoPulse(convertToPulse(value));
    }
  }
  
  public Integer getValue() {
    return value;
  }

  private float convertToPulse(int power) {
    return ((float) power / VALUE_TO_PULSE_FACTOR) + CENTER_PULSE;
  }
  
  private void delay(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      //
    }
  }
}
