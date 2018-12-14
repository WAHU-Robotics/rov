/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import me.jbuelow.rov.common.Ping;
import me.jbuelow.rov.common.Pong;
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.wet.service.CommandHandler;
import org.springframework.stereotype.Service;

/**
 * @author Brian Wachsmuth
 */
@Service
public class PingHandler implements CommandHandler<Ping> {

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#execute(Command)
   */
  @Override
  public Response execute(Ping command) {
    return new Pong();
  }

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#getCommandType()
   */
  @Override
  public Class<Ping> getCommandType() {
    return Ping.class;
  }

}
