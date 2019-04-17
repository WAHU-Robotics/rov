/**
 *
 */
package me.jbuelow.rov.common.object;

import java.io.Serializable;
import java.util.UUID;
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

  private UUID id;
  private int power;  //0=stop, 1000=full power forward, -1000=full power reverse
}
