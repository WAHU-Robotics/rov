package org.snapshotscience.rov.wet.service.impl;

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

import org.snapshotscience.rov.common.command.GetWaterTemp;
import org.snapshotscience.rov.common.response.Response;
import org.snapshotscience.rov.common.response.WaterTemp;
import org.snapshotscience.rov.wet.service.CommandHandler;
import org.snapshotscience.rov.wet.service.TempSensorService;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
public class GetWaterTempHandler implements CommandHandler<GetWaterTemp> {

  private TempSensorService tempSensorService;

  public GetWaterTempHandler(TempSensorService tempSensorService) {
    this.tempSensorService = tempSensorService;
  }

  @Override
  public Response execute(GetWaterTemp command) {
    return new WaterTemp(tempSensorService.getTemp());
  }

  @Override
  public Class<GetWaterTemp> getCommandType() {
    return GetWaterTemp.class;
  }

}
