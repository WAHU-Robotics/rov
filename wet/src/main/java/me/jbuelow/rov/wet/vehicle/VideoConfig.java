/**
 *
 */
package me.jbuelow.rov.wet.vehicle;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jbuelow.rov.common.capabilities.Video;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VideoConfig extends Video implements AccessoryConfig {

  private static final long serialVersionUID = -7260327768559672636L;

  @Override
  public void validateConfigurtion() {
    // TODO Auto-generated method stub

  }

}
