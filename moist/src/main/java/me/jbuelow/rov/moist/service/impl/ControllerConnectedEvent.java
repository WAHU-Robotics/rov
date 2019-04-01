/**
 *
 */
package me.jbuelow.rov.moist.service.impl;

import org.springframework.context.ApplicationEvent;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class ControllerConnectedEvent extends ApplicationEvent {

  public ControllerConnectedEvent(Object source) {
    super(source);
  }
}