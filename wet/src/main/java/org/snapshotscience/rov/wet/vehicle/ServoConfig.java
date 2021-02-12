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

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.snapshotscience.rov.common.capabilities.Servo;
import org.snapshotscience.rov.common.capabilities.Tool;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServoConfig extends Servo implements ActuatorConfig {

  private static final long serialVersionUID = -4496116153451016803L;

  @NotBlank
  private int pwmPort;

  @NotBlank
  private Tool toolType;

  @Override
  public void validateConfigurtion() {
    // TODO Auto-generated method stub

  }
}
