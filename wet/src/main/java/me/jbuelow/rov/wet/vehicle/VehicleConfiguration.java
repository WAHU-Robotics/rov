package me.jbuelow.rov.wet.vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jacob Buelow
 *
 * Loads our vehicles capability configurtion from our properties file(s)
 */
@Configuration
@ConfigurationProperties(prefix = "vehicle")
@Data
public class VehicleConfiguration {

  private UUID id;
  private String name;
  private List<VideoConfig> videoConfiguration = new ArrayList<>();
  private List<MotorConfig> motorConfiguration = new ArrayList<>();
  private List<ServoConfig> servoConfiguration = new ArrayList<>();

  public VehicleConfiguration() {
    this.id = UUID.randomUUID();
  }

  public List<AccessoryConfig> getAllConfiguration() {
    List<AccessoryConfig> allConfig = new ArrayList<>();
    allConfig.addAll(videoConfiguration);
    allConfig.addAll(motorConfiguration);
    allConfig.addAll(servoConfiguration);

    return allConfig;
  }
}
