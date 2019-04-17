package me.jbuelow.rov.wet.vehicle;

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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.UUID;
import me.jbuelow.rov.common.capabilities.Capability;
import me.jbuelow.rov.common.capabilities.Motor;
import me.jbuelow.rov.common.capabilities.MotorOrientation;
import me.jbuelow.rov.common.capabilities.MotorType;
import me.jbuelow.rov.common.capabilities.Servo;
import me.jbuelow.rov.common.capabilities.Video;
import org.junit.Test;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class CapabilityFactoryTest {

  /**
   * Test method for {@link me.jbuelow.rov.wet.vehicle.CapabilityFactory#getCapability(me.jbuelow.rov.wet.vehicle.AccessoryConfig)}.
   */
  @Test
  public void testGetCapabilityMotor() {
    MotorConfig config = new MotorConfig();
    config.setMotorOrientation(MotorOrientation.Y_AXIS);
    config.setMotorType(MotorType.TRACK);
    config.setName("Test1");
    config.setPwmPort(5);

    Capability capability = CapabilityFactory.getCapability(config);

    assertThat(capability, instanceOf(Motor.class));
    Motor motor = (Motor) capability;
    assertThat(motor.getId(), is(config.getId()));
    assertThat(motor.getMotorOrientation(), is(config.getMotorOrientation()));
    assertThat(motor.getMotorType(), is(config.getMotorType()));
    assertThat(motor.getName(), is(config.getName()));
  }

  /**
   * Test method for {@link me.jbuelow.rov.wet.vehicle.CapabilityFactory#getCapability(me.jbuelow.rov.wet.vehicle.AccessoryConfig)}.
   */
  @Test
  public void testGetCapabilityServo() {
    ServoConfig config = new ServoConfig();
    config.setName("Test1");
    config.setPwmPort(5);

    Capability capability = CapabilityFactory.getCapability(config);

    assertThat(capability, instanceOf(Servo.class));
    Servo servo = (Servo) capability;
    assertThat(servo.getId(), is(config.getId()));
    assertThat(servo.getName(), is(config.getName()));
  }

  /**
   * Test method for {@link me.jbuelow.rov.wet.vehicle.CapabilityFactory#getCapability(me.jbuelow.rov.wet.vehicle.AccessoryConfig)}.
   */
  @Test
  public void testGetCapabilityVideo() {
    VideoConfig config = new VideoConfig();
    config.setName("Test1");
    config.setVideoStreamAddress("localhost:12345");

    Capability capability = CapabilityFactory.getCapability(config);

    assertThat(capability, instanceOf(Video.class));
    Video video = (Video) capability;
    assertThat(video.getId(), is(config.getId()));
    assertThat(video.getName(), is(config.getName()));
    assertThat(video.getVideoStreamAddress(), is(config.getVideoStreamAddress()));
  }

  /**
   * Test method for {@link me.jbuelow.rov.wet.vehicle.CapabilityFactory#getCapability(me.jbuelow.rov.wet.vehicle.AccessoryConfig)}.
   */
  @Test(expected=IllegalArgumentException.class)
  public void testGetCapabilityInvalid() {
    AccessoryConfig config = new AccessoryConfig() {

      @Override
      public String getName() {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public UUID getId() {
        // TODO Auto-generated method stub
        return UUID.randomUUID();
      }

      @Override
      public void validateConfigurtion() {
        // TODO Auto-generated method stub

      }
    };

    CapabilityFactory.getCapability(config);
  }
}
