/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Slf4j
public class ControllerDisconnectedEvent extends ApplicationEvent {

  /**
   * 
   */
  private static final long serialVersionUID = 4337298704611297359L;

  public ControllerDisconnectedEvent(Object source) {
    super(source);
  }
}
