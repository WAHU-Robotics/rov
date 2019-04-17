/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    float temp;
    File file = new File("/sys/class/thermal/thermal_zone0/temp");
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(file));
      int t = Integer.parseInt(reader.readLine());
      temp = ((float) t) / 1000;
    } catch (IOException e) {
      temp = 0.0f;
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
