package org.snapshotscience.rov.wet.service;

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

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import org.snapshotscience.rov.common.capabilities.Tool;

public interface ServoService {
  void setServoValue(Tool tool, int power) throws IOException;
  
  int getServoValue(Tool tool);
  
  UUID getServoByName(String name);

  void setValues(Map<Tool, Integer> thrustVectors);
}
