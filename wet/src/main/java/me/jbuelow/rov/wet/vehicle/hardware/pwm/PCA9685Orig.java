package me.jbuelow.rov.wet.vehicle.hardware.pwm;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import java.io.IOException;
import java.util.Objects;

/**
 * Servo Driver, <a href="https://www.adafruit.com/product/815">https://www.adafruit.com/product/815</a>
 * <br/> In theory, PWM servos support those values:
 * <pre>
 * Servo Pulse | Standard |   Continuous
 * ------------+----------+-------------------
 *       1.5ms |   0 deg  |     Stop
 *       2.0ms |  90 deg  | FullSpeed forward
 *       1.0ms | -90 deg  | FullSpeed backward
 * ------------+----------+-------------------
 * </pre>
 * <b><i>BUT</i></b> this may vary a lot.<br/>
 * Servos like <a href="https://www.adafruit.com/product/169">https://www.adafruit.com/product/169</a>
 * or <a href="https://www.adafruit.com/product/155">https://www.adafruit.com/product/155</a> have
 * min and max values like 0.5ms 2.5ms, which is quite different. Servos are analog devices...
 *
 * The best is probably to calibrate the servos before using them.<br/> For that, you can use some
 * utility functions provided below:
 * <ul>
 * <li> {@link PCA9685Orig#getPulseFromValue(int, int)}</li>
 * <li> {@link PCA9685Orig#getServoMinValue(int)}</li>
 * <li> {@link PCA9685Orig#getServoCenterValue(int)}</li>
 * <li> {@link PCA9685Orig#getServoMaxValue(int)}</li>
 * <li> {@link PCA9685Orig#getServoValueFromPulse(int, float)}</li>
 * </ul>
 */
//@Component
@Slf4j
//@Profile("!noHardware")
public class PCA9685Orig implements DisposableBean {
  // "Theoretical" values
  private final static float CENTER_PULSE = 1.5f;
  private final static float MIN_PULSE = 1f;
  private final static float MAX_PULSE = 2f;


  public final static int PCA9685_ADDRESS = 0x40;

  public final static int MODE1 = 0x00;
  public final static int MODE2 = 0x01;

  public final static int SUBADR1 = 0x02;
  public final static int SUBADR2 = 0x03;
  public final static int SUBADR3 = 0x04;

  public final static int PRESCALE = 0xFE;
  public final static int LED0_ON_L = 0x06;
  public final static int LED0_ON_H = 0x07;
  public final static int LED0_OFF_L = 0x08;
  public final static int LED0_OFF_H = 0x09;

  public final static int ALL_LED_ON_L = 0xFA;
  public final static int ALL_LED_ON_H = 0xFB;
  public final static int ALL_LED_OFF_L = 0xFC;
  public final static int ALL_LED_OFF_H = 0xFD;

  private int freq = 60;

  private I2CBus bus;
  private I2CDevice servoDriver;

  public PCA9685Orig() throws I2CFactory.UnsupportedBusNumberException {
    this(PCA9685_ADDRESS); // 0x40 obtained through sudo i2cdetect -y 1
  }

  public PCA9685Orig(int address) throws I2CFactory.UnsupportedBusNumberException {
    if (Objects.equals(System.getenv("ROV_NOPI4J"), "true")) {
      return;
    }
    try {
      // Get I2C bus
      bus = I2CFactory.getInstance(I2CBus.BUS_1); // Depends on the RasPi version
      log.debug("Connected to bus. OK.");

      // Get the device itself
      servoDriver = bus.getDevice(address);
      log.debug("Connected to device. OK.");

      // Reseting
      servoDriver.write(MODE1, (byte) 0x00);
    } catch (IOException e) {
      throw new RuntimeException("Error opening PWM Interface.", e);
    }
  }

  /**
   * @param freq 40..1000
   */
  public void setPWMFreq(int freq) {
    this.freq = freq;
    float preScaleVal = 25_000_000.0f; // 25MHz
    preScaleVal /= 4_096.0;            // 4096: 12-bit
    preScaleVal /= freq;
    preScaleVal -= 1.0;
    log.debug("Setting PWM frequency to " + freq + " Hz");
    log.debug("Estimated pre-scale: " + preScaleVal);

    double preScale = Math.floor(preScaleVal + 0.5);
    log.debug("Final pre-scale: " + preScale);
    
    try {
      byte oldmode = (byte) servoDriver.read(MODE1);
      byte newmode = (byte) ((oldmode & 0x7F) | 0x10); // sleep
      servoDriver.write(MODE1, newmode);               // go to sleep
      servoDriver.write(PRESCALE, (byte) (Math.floor(preScale)));
      servoDriver.write(MODE1, oldmode);
      delay(5);
      servoDriver.write(MODE1, (byte) (oldmode | 0x80));
    } catch (IOException ioe) {
      throw new RuntimeException("Error setting PWM Frequency", ioe);
    }
  }

  private void delay(int i) {
    try {
      Thread.sleep(i);
    } catch (InterruptedException ignored) {
    }
  }

  /**
   * @param channel 0..15
   * @param on 0..4095 (2^12 positions)
   * @param off 0..4095 (2^12 positions)
   */
  public void setPWM(int channel, int on, int off) throws IllegalArgumentException {
    if (channel < 0 || channel > 15) {
      throw new IllegalArgumentException("Channel must be in [0, 15]");
    }
    if (on < 0 || on > 4_095) {
      throw new IllegalArgumentException("On must be in [0, 4095]");
    }
    if (off < 0 || off > 4_095) {
      throw new IllegalArgumentException("Off must be in [0, 4095]");
    }
    if (on > off) {
      throw new IllegalArgumentException("OFF must be greater than ON");
    }
    try {
      servoDriver.write(LED0_ON_L + 4 * channel, (byte) (on & 0xFF));
      servoDriver.write(LED0_ON_H + 4 * channel, (byte) (on >> 8));
      servoDriver.write(LED0_OFF_L + 4 * channel, (byte) (off & 0xFF));
      servoDriver.write(LED0_OFF_H + 4 * channel, (byte) (off >> 8));
    } catch (IOException ioe) {
      throw new RuntimeException("Error setting PWM output.", ioe);
    }
  }

  /**
   * @param channel 0..15
   * @param pulseMS in ms.
   */
  public void setServoPulse(int channel, float pulseMS) {
    int pulse = getServoValueFromPulse(this.freq, pulseMS);
    this.setPWM(channel, 0, pulse);
  }

  /**
   * Free resources
   */
  @Override
  public void destroy() throws Exception {
    if (this.bus != null) {
      try {
        this.bus.close();
      } catch (Exception ex) {
        log.error("Error closing I2C connection.", ex);
      }
    }
  }

  /**
   * @param freq in Hz
   * @param targetPulse in ms
   * @return the value as an int
   */
  public int getServoValueFromPulse(int freq, float targetPulse) {
    double pulseLength = 1_000_000; // 1s = 1,000,000 us per pulse. "us" is to be read "micro (mu) sec".
    pulseLength /= freq;  // 40..1000 Hz
    pulseLength /= 4_096; // 12 bits of resolution. 4096 = 2^12
    int pulse = (int) Math.round((targetPulse * 1_000) / pulseLength); // in millisec
    if (log.isDebugEnabled()) {
      log.debug(
          String.format("%.04f \u00b5s per bit, pulse: %d", pulseLength, pulse)); // bit? cycle?
    }
    
    return pulse;
  }

  public double getPulseFromValue(int freq, int value) {
    double msPerPeriod = 1_000.0 / (double) freq;
    return msPerPeriod * ((double) value / 4_096.0);
  }

  public int getServoMinValue(int freq) {
    return getServoValueFromPulse(freq, MIN_PULSE);
  }

  public int getServoCenterValue(int freq) {
    return getServoValueFromPulse(freq, CENTER_PULSE);
  }

  public int getServoMaxValue(int freq) {
    return getServoValueFromPulse(freq, MAX_PULSE);
  }
}
