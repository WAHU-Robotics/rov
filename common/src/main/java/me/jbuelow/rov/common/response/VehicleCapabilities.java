package me.jbuelow.rov.common.response;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.jbuelow.rov.common.capabilities.Capability;

/**
 * @author Jacob Buelow
 *
 * This class is used to respond to the Hello broadcast.
 *
 * We will respond with our address and our vehicles capabilities.
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class VehicleCapabilities extends Response {

  private static final long serialVersionUID = 8772769635819601589L;

  private UUID id;
  private String name;
  private List<Capability> capabilities = new ArrayList<>();
}
