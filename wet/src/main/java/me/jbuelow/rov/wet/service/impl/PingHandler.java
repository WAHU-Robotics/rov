/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import java.util.Random;
import me.jbuelow.rov.common.command.Ping;
import me.jbuelow.rov.common.response.Pong;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.wet.service.CommandHandler;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
public class PingHandler implements CommandHandler<Ping> {

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#execute(Command)
   */
  @Override
  public Response execute(Ping command) {
    Random r = new Random();
    Pong response = new Pong(String.valueOf(r.nextInt(255)));
    return response;
  }

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#getCommandType()
   */
  @Override
  public Class<Ping> getCommandType() {
    return Ping.class;
  }

}
