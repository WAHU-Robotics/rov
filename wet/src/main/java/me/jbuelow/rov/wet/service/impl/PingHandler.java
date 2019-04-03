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

  /**
   * Handles the Ping command
   *
   * @param command Ping instance
   * @return Pong instance
   */
  @Override
  public Response execute(Ping command) {
    Random r = new Random();
    Pong response = new Pong(String.valueOf(r.nextInt(255)));
    return response;
  }


  /**
   * Gives the class of the command this handler is supposed to handle
   *
   * @return class instance of Ping
   */
  @Override
  public Class<Ping> getCommandType() {
    return Ping.class;
  }

}
