package org.snapshotscience.rov.wet.vehicle;

/* This file is part of WAHU ROV Software.
 *
 * WAHU ROV Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WAHU ROV Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WAHU ROV Software.  If not, see <https://www.gnu.org/licenses/>.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
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
  
  @PostConstruct
  public void ValidateVehicleConfiguration() {
    for (AccessoryConfig config : getAllConfiguration()) {
      config.validateConfigurtion();
    }
  }
}
