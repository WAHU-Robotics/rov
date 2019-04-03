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
public class VideoConfig extends Video implements AccessoryConfig {@Override
  public void validateConfigurtion() {
    // TODO Auto-generated method stub
    
  }

}
