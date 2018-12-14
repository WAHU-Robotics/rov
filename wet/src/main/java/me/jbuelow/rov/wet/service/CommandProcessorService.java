/**
 * 
 */
package me.jbuelow.rov.wet.service;

import me.jbuelow.rov.common.Command;
import me.jbuelow.rov.common.Response;

/**
 * @author Brian Wachsmuth 
 *
 */
public interface CommandProcessorService {
  Response handleCommand(Command command);
}
