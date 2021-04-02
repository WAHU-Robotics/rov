package org.snapshotscience.rov.wet.service.impl;

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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import java.util.Map;
import java.util.Properties;
import org.snapshotscience.rov.common.command.GetSystemStats;
import org.snapshotscience.rov.common.response.Response;
import org.snapshotscience.rov.common.response.SystemStats;
import org.junit.Before;
import org.junit.Test;

public class GetSystemStatsHandlerTest {

  private GetSystemStatsHandler handler;

  @Before
  public void setup() {
    handler = new GetSystemStatsHandler();
  }

  @Test
  public void testExecute() {
    GetSystemStats command = new GetSystemStats(true, true);
    Response response = handler.execute(command);

    assertThat(response, instanceOf(SystemStats.class));
    assertThat(response.getTimestamp(), instanceOf(Instant.class));

    SystemStats responseStats = (SystemStats) response;
    assertThat(responseStats.getProperties(), instanceOf(Properties.class));
    assertThat(responseStats.getEnvironment(), instanceOf(Map.class));
  }

  @Test
  public void testGetCommandType() {
    assertThat(handler.getCommandType(), is(equalTo(GetSystemStats.class)));
  }

}
