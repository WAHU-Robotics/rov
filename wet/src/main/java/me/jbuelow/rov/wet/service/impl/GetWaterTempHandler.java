/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import me.jbuelow.rov.common.command.GetWaterTemp;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.WaterTemp;
import me.jbuelow.rov.wet.service.CommandHandler;
import me.jbuelow.rov.wet.service.SensorService;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
public class GetWaterTempHandler implements CommandHandler<GetWaterTemp> {

  private SensorService sensorService;

  public GetWaterTempHandler(SensorService sensorService) {
    this.sensorService = sensorService;
  }

  @Override
  public Response execute(GetWaterTemp command) {
    return new WaterTemp(sensorService.getTemp());
  }

  @Override
  public Class<GetWaterTemp> getCommandType() {
    return GetWaterTemp.class;
  }

}
