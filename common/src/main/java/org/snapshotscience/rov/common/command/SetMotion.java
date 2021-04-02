package org.snapshotscience.rov.common.command;

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

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.snapshotscience.rov.common.capabilities.ThrustAxis;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class SetMotion extends Command {
  /**
   * 
   */
  private static final long serialVersionUID = 3803795320272750470L;

  private Map<ThrustAxis, Integer> thrustVectors = new HashMap<>();
}