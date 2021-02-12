package org.snapshotscience.rov.common.response;

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

import lombok.Getter;

public class Goodbye extends Response {
  private static final long serialVersionUID = -6435681174573067030L;

  @Getter
  private boolean safe;

  @Getter
  private Thread thread;

  public Goodbye() {
    this(false);
  }

  public Goodbye(boolean safe) {
    this(safe, null);
  }

  public Goodbye(Thread thread) {
    this(false, thread);
  }

  public Goodbye(boolean safe, Thread thread) {
    this.safe = safe;
    this.thread = thread;
  }
}
