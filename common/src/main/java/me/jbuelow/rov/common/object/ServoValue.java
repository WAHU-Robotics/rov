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
public class ServoValue implements Serializable {

  private static final long serialVersionUID = 2812485476244706951L;

  private UUID id;
  private int power;
}
