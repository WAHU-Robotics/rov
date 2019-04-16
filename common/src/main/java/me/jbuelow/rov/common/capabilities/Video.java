package me.jbuelow.rov.common.capabilities;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Video extends AbstractCapability {

  /**
   * 
   */
  private static final long serialVersionUID = -5087385901699169805L;

  private String videoStreamAddress;

  public Video() {
    super();
  }

  public Video(UUID id) {
    super(id);
  }
}
