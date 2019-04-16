package me.jbuelow.rov.wet.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.command.SetMotors;
import me.jbuelow.rov.common.object.MotorPower;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.SetMotorsResponse;
import me.jbuelow.rov.wet.service.CommandHandler;
import me.jbuelow.rov.wet.service.MotorService;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
@Slf4j
public class SetMotorsHandler implements CommandHandler<SetMotors> {

  private final MotorService motorService;
  
  public SetMotorsHandler(MotorService motorService) {
    this.motorService = motorService;
  }


  /**
   * Handles the SetMotors command
   *
   * @param command SetMotors instance
   * @return SetMotorsResponse instance
   */
  @Override
  public Response execute(SetMotors command) {
    try {
      log.debug("Got Set Motors Command!");
      for (MotorPower motor : command.getPowerLevels()) {
        motorService.setMotorPower(motor.getId(), motor.getPower());
      }

      return new SetMotorsResponse(true);
    } catch (Exception e) {
      log.error("Error setting motor power levels", e);
      return new SetMotorsResponse(false, e);
    }
  }

  /**
   * Gives the class of the command this handler is supposed to handle
   *
   * @return class instance of SetMotors
   */
  @Override
  public Class<SetMotors> getCommandType() {
    return SetMotors.class;
  }
}
