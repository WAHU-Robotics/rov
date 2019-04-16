package me.jbuelow.rov.wet.service;

import me.jbuelow.rov.common.command.Command;
import me.jbuelow.rov.common.response.Response;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public interface CommandHandler<T extends Command> {

  Response execute(T command);

  Class<T> getCommandType();
}
