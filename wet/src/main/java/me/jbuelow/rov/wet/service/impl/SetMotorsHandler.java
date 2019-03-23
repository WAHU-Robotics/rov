/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.validation.constraints.Null;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.MotorPower;
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.common.SetMotors;
import me.jbuelow.rov.common.SetMotorsResponse;
import me.jbuelow.rov.wet.service.CommandHandler;
import me.jbuelow.rov.wet.vehicle.hardware.PCA9685;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
@Slf4j
public class SetMotorsHandler implements CommandHandler<SetMotors> {

  @Autowired
  PCA9685 driver;

  public SetMotorsHandler() {}

  @PostConstruct
  public void init() {
    try {
      if (Objects.equals(System.getenv("ROV_NOPI4J"), "true")) {
        driver = null;
      } else {
        try {
          driver.setPWMFreq(60);
        } catch (NullPointerException e) {
          e.printStackTrace();
        }
      }
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
    }
  }

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#execute(Command)
   */
  @Override
  public Response execute(SetMotors command) {
    try {
      log.debug("Got Set Motors Command!");
      for (MotorPower motor : command.getPowerLevels()) {
        //log.debug("Motor " + motor.getId() + " power: " + motor.getPower());
        float p = convertToPulse(motor.getPower());
        //log.debug("Motor " + motor.getId() + " pulse: " + p);
        //heck
        try {
          driver.setServoPulse(motor.getId(), p);
        } catch (NullPointerException ignored) {
        }
      }
      return new SetMotorsResponse(true);
    } catch (Exception e) {
      e.printStackTrace();
      return new SetMotorsResponse(false);
    }
  }

  private float convertToPulse(int power) {
    return ((float) power / 2000f) + 1.5f;
  }

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#getCommandType()
   */
  @Override
  public Class<SetMotors> getCommandType() {
    return SetMotors.class;
  }

}
