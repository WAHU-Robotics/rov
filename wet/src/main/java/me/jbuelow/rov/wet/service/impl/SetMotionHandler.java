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
import me.jbuelow.rov.common.command.SetMotion;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.SetMotionResponse;
import me.jbuelow.rov.wet.service.CommandHandler;
import me.jbuelow.rov.wet.service.MotionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetMotionHandler implements CommandHandler<SetMotion> {

  private MotionService motionService;

  public SetMotionHandler(MotionService motionService) {
    this.motionService = motionService;
  }
  
  @Override
  public Response execute(SetMotion command) {
    try {
      log.debug("Got Set Motion Command!");
      motionService.setMotion(command.getThrustVectors());

      return new SetMotionResponse(true);
    } catch (Exception e) {
      log.error("Error setting motor power levels", e);
      return new SetMotionResponse(false, e);
    }
  }

  @Override
  public Class<SetMotion> getCommandType() {
    return SetMotion.class;
  }
}
