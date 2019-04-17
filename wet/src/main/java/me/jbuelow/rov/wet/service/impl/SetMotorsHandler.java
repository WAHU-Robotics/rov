package me.jbuelow.rov.wet.service.impl;

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

  private MotorService motorService;

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
