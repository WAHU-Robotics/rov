package me.jbuelow.rov.wet.service.impl;

import me.jbuelow.rov.common.command.Shutdown;
import me.jbuelow.rov.common.response.Goodbye;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.wet.service.CommandHandler;

public class ShutdownHandler implements CommandHandler<Shutdown> {

  /**
   * Handles the Shutdown command
   *
   * @param command Shutdown instance
   * @return Goodbye instance
   */
  @Override
  public Response execute(Shutdown command) {
    return new Goodbye();
  }


  /**
   * Gives the class of the command this handler is supposed to handle
   *
   * @return class instance of Shutdown
   */
  @Override
  public Class<Shutdown> getCommandType() {
    return Shutdown.class;
  }

}
