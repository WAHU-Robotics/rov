package me.jbuelow.rov.wet.service.impl;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.jbuelow.rov.common.capabilities.Capability;
import me.jbuelow.rov.common.command.GetCapabilities;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.VehicleCapabilities;
import me.jbuelow.rov.wet.vehicle.AccessoryConfig;
import me.jbuelow.rov.wet.vehicle.MotorConfig;
import me.jbuelow.rov.wet.vehicle.ServoConfig;
import me.jbuelow.rov.wet.vehicle.VehicleConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Import(GetCapabilitiesHandler.class)
public class GetCapabilitiesHandlerTest {
  private static final UUID TEST_UUID = UUID.randomUUID();
  
  @MockBean
  private
  VehicleConfiguration vehicleConfiguration;
  
  @Autowired
  private
  GetCapabilitiesHandler handler;

  /**
   * Test method for {@link me.jbuelow.rov.wet.service.impl.GetCapabilitiesHandler#execute(GetCapabilities)}.
   */
  @Test
  public void testExecute() {
    when(vehicleConfiguration.getId()).thenReturn(TEST_UUID);
    when(vehicleConfiguration.getName()).thenReturn("Test Vehicle");
    
    List<AccessoryConfig> accessories = new ArrayList<>();
    accessories.add(new MotorConfig());
    accessories.add(new ServoConfig());
    
    when(vehicleConfiguration.getAllConfiguration()).thenReturn(accessories);
    
    
    Response response = handler.execute(new GetCapabilities());
    
    assertThat(response, instanceOf(VehicleCapabilities.class));
    
    VehicleCapabilities vehicleCapabilities = (VehicleCapabilities) response;
    assertThat(vehicleCapabilities.getId(), is(TEST_UUID));
    assertThat(vehicleCapabilities.getName(), is("Test Vehicle"));
    
    List<Capability> capabilities = vehicleCapabilities.getCapabilities();
    assertThat(capabilities, instanceOf(ArrayList.class));
    assertThat(capabilities.size(), is(3));
  }

}
