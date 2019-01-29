/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.common.SetMotors;
import me.jbuelow.rov.common.SetMotorsResponse;
import me.jbuelow.rov.wet.service.CommandHandler;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
@Slf4j
public class SetMotorsHandler implements CommandHandler<SetMotors> {

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#execute(Command)
   */
  @Override
  public Response execute(SetMotors command) {
    log.debug("Got Set Motors Command!");
    return new SetMotorsResponse(true);
  }

  /* (non-Javadoc)
   * @see net.wachsmuths.rov.service.CommandHandler#getCommandType()
   */
  @Override
  public Class<SetMotors> getCommandType() {
    return SetMotors.class;
  }

}
