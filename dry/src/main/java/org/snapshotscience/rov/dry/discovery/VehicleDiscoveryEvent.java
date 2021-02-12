package org.snapshotscience.rov.dry.discovery;

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

import java.net.InetAddress;
import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Getter
public class VehicleDiscoveryEvent extends ApplicationEvent {

  /**
   *
   */
  private static final long serialVersionUID = -4288932705637545744L;

  private InetAddress vehicleAddress;
  private UUID vehicleID;

  public VehicleDiscoveryEvent(Object source, InetAddress vehicleAddress) {
    super(source);
    this.vehicleAddress = vehicleAddress;
  }

  public void setVehicleID(UUID vehicleID) {
    this.vehicleID = vehicleID;
  }

}
