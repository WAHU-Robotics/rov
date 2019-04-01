/**
 *
 */
package me.jbuelow.rov.moist.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Slf4j
public class ControllerDisconnectedEvent extends ApplicationEvent {

  public ControllerDisconnectedEvent(Object source) {
    super(source);
  }
}
