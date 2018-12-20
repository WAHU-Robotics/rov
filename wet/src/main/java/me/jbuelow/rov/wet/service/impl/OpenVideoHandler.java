/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import me.jbuelow.rov.common.OpenVideo;
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.common.VideoStreamAddress;
import me.jbuelow.rov.wet.service.CommandHandler;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
public class OpenVideoHandler implements CommandHandler<OpenVideo> {

  /* (non-Javadoc)
   * @see me.jbuelow.rov.service.CommandHandler#execute(Command)
   */
  @Override
  public Response execute(OpenVideo command) {
    return new VideoStreamAddress(command.port);
  }

  /* (non-Javadoc)
   * @see me.jbuelow.rov.service.CommandHandler#getCommandType()
   */
  @Override
  public Class<OpenVideo> getCommandType() {
    return OpenVideo.class;
  }

}
