/**
 *
 */
package me.jbuelow.rov.common.capabilities;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Brian Wachsmuth
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Video extends AbstractCapability {

  private String videoStreamAddress;
}
