/**
 * 
 */
package me.jbuelow.rov.wet.service.impl;

import org.springframework.context.ApplicationEvent;

/**
 * @author Brian Wachsmuth
 *
 */
public class ControllerConnectedEvent extends ApplicationEvent {

  public ControllerConnectedEvent(Object source) {
    super(source);
  }
}
