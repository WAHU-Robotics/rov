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

import me.jbuelow.rov.common.command.GetWaterTemp;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.WaterTemp;
import me.jbuelow.rov.wet.service.CommandHandler;
import me.jbuelow.rov.wet.service.SensorService;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
public class GetWaterTempHandler implements CommandHandler<GetWaterTemp> {

  private SensorService sensorService;

  public GetWaterTempHandler(SensorService sensorService) {
    this.sensorService = sensorService;
  }

  @Override
  public Response execute(GetWaterTemp command) {
    return new WaterTemp(sensorService.getTemp());
  }

  @Override
  public Class<GetWaterTemp> getCommandType() {
    return GetWaterTemp.class;
  }

}
