package me.jbuelow.rov.common;

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

import java.nio.charset.Charset;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public abstract class RovConstants {

  public static final int DISCOVERY_BCAST_PORT = 4444;
  public static final Charset CHARSET = Charset.forName("UTF-8");
  public static byte[] DISCOVERY_BYTES = "YEET".getBytes(CHARSET);
  public static final int DISCOVERY_PACKET_SIZE = DISCOVERY_BYTES.length;
  public static final int ROV_PORT = 8888;

}
