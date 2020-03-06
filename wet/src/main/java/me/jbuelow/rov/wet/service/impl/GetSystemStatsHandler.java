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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.measure.Measurable;
import javax.measure.Measure;
import javax.measure.quantity.Temperature;
import javax.measure.unit.SI;
import me.jbuelow.rov.common.command.GetSystemStats;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.SystemStats;
import me.jbuelow.rov.wet.service.CommandHandler;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
public class GetSystemStatsHandler implements CommandHandler<GetSystemStats> {


  /**
   * Handles the GetSystemStats command
   *
   * @param command GetSystemStats instance
   * @return SystemStats instance
   */
  @Override
  public Response execute(GetSystemStats command) {
    SystemStats response = new SystemStats();

    // Package environment variables if requested by host
    if (command.getEnvironment) {
      response.setEnvironment(System.getenv());
    }

    // Package system properties if requested by host
    if (command.getProperties) {
      response.setProperties(System.getProperties());
    }

    Measurable<Temperature> temp;
    File file = new File("/sys/class/thermal/thermal_zone0/temp");
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(file));
      int t = Integer.parseInt(reader.readLine());
      Number num = ((float) t) / 1000;
      temp = Measure.valueOf(num.doubleValue(), SI.CELSIUS);
    } catch (IOException e) {
      temp = Measure.valueOf(0, SI.KELVIN);
    } finally {
      if (reader != null) {
        IOUtils.closeQuietly(reader);
      }
    }

    response.setCpuTemp(temp);

    return response;
  }


  /**
   * Gives the class of the command this handler is supposed to handle
   *
   * @return class instance of GetSystemStats
   */
  @Override
  public Class<GetSystemStats> getCommandType() {
    return GetSystemStats.class;
  }

}
