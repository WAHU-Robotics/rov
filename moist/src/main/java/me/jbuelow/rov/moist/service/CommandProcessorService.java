/**
 *
 */
package me.jbuelow.rov.moist.service;

import me.jbuelow.rov.common.Command;
import me.jbuelow.rov.common.Response;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public interface CommandProcessorService {

  Response handleCommand(Command command);
}
