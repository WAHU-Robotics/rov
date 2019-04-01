/**
 *
 */
package me.jbuelow.rov.moist.service.impl;

import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.MotorPower;
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.common.SetMotors;
import me.jbuelow.rov.common.SetMotorsResponse;
import me.jbuelow.rov.common.exception.InvalidMotorCountException;
import me.jbuelow.rov.moist.service.CommandHandler;
import me.jbuelow.rov.moist.vehicle.hardware.DCMotorDriver;
import me.jbuelow.rov.moist.vehicle.hardware.DCMotorDriver.state;
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
  DCMotorDriver driver;

  public SetMotorsHandler() {}

  @PostConstruct
  public void init() {
    try {
      if (Objects.equals(System.getenv("ROV_NOPI4J"), "true")) {
        driver = null;
      } else {
        try {
          driver.initialize();
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
      List<MotorPower> motors = command.getPowerLevels();
      if (motors.size() != 1) {
        return new SetMotorsResponse(false, new InvalidMotorCountException());
      }

      if (motors.get(0).getPower() >= 1) {
        driver.setMotorState(state.HIGH);
      } else {
        driver.setMotorState(state.LOW);
      }
      return new SetMotorsResponse(true);
    } catch (Exception e) {
      e.printStackTrace();
      return new SetMotorsResponse(false, e);
    }
  }

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#getCommandType()
   */
  @Override
  public Class<SetMotors> getCommandType() {
    return SetMotors.class;
  }

}
