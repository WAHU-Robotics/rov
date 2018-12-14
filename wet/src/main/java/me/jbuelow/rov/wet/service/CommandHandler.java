/**
 *
 */
package me.jbuelow.rov.wet.service;

import me.jbuelow.rov.common.Command;
import me.jbuelow.rov.common.Response;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public interface CommandHandler<T extends Command> {

  Response execute(T command);

  Class<T> getCommandType();
}
