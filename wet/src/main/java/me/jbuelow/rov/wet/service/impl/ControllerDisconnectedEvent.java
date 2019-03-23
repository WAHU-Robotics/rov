/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.wet.vehicle.hardware.PCA9685;
import org.springframework.beans.factory.annotation.Autowired;
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
