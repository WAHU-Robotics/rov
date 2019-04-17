/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import org.springframework.context.ApplicationEvent;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class ControllerConnectedEvent extends ApplicationEvent {

  /**
   *
   */
  private static final long serialVersionUID = 6504651068190183804L;

  public ControllerConnectedEvent(Object source) {
    super(source);
  }
}
