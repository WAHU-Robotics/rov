package me.jbuelow.rov.wet.vehicle.hardware;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.io.IOException;
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
