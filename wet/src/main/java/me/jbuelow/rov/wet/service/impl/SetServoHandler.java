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
import me.jbuelow.rov.common.command.SetServo;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.SetMotionResponse;
import me.jbuelow.rov.common.response.SetServoResponse;
import me.jbuelow.rov.wet.service.CommandHandler;
import me.jbuelow.rov.wet.service.ServoService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetServoHandler implements CommandHandler<SetServo> {

  private ServoService servoService;

  public SetServoHandler(ServoService servoService) {
    this.servoService = servoService;
  }
  
  @Override
  public Response execute(SetServo command) {
    try {
      log.debug("Got Set Motion Command!");
      servoService.setValues(command.getServoValues());

      return new SetServoResponse(true);
    } catch (Exception e) {
      log.error("Error setting servo power levels", e);
      return new SetMotionResponse(false, e);
    }
  }

  @Override
  public Class<SetServo> getCommandType() {
    return SetServo.class;
  }
}
