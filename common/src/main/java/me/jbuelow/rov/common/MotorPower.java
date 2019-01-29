/**
 *
 */
package me.jbuelow.rov.common;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MotorPower implements Serializable {

  private static final long serialVersionUID = 2812485476244706951L;

  private int id;
  private int power;  //percentage; 0=stop, 100=full power forward, -100=full power reverse
}
