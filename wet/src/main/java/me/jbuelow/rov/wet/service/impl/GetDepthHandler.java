package me.jbuelow.rov.wet.service.impl;

import me.jbuelow.rov.common.command.GetDepth;
import me.jbuelow.rov.common.response.Depth;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.wet.service.CommandHandler;
import me.jbuelow.rov.wet.service.DepthSensorService;
import org.springframework.stereotype.Service;

@Service
public class GetDepthHandler implements CommandHandler<GetDepth> {

  private final DepthSensorService sensorService;

  public GetDepthHandler(DepthSensorService sensorService) {
    this.sensorService = sensorService;
  }

  @Override
  public Response execute(GetDepth command) {
    return new Depth(sensorService.getDepth());
  }

  @Override
  public Class<GetDepth> getCommandType() {
    return GetDepth.class;
  }

}
