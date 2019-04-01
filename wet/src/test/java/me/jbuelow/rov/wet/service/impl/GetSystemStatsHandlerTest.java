package me.jbuelow.rov.wet.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import java.util.Map;
import java.util.Properties;
import me.jbuelow.rov.common.command.GetSystemStats;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.SystemStats;
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
