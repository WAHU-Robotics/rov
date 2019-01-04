/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import me.jbuelow.rov.common.GetSystemStats;
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.common.SystemStats;
import me.jbuelow.rov.wet.service.CommandHandler;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
public class GetSystemStatsHandler implements CommandHandler<GetSystemStats> {

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#execute(Command)
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

    response.setRequest(
        command); //TODO: Find a way to make this line automatic in the constructor for Response

    return response;
  }

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#getCommandType()
   */
  @Override
  public Class<GetSystemStats> getCommandType() {
    return GetSystemStats.class;
  }

}
