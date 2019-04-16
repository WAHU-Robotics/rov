package me.jbuelow.rov.wet.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.command.SetMotion;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.SetMotionResponse;
import me.jbuelow.rov.wet.service.CommandHandler;
import me.jbuelow.rov.wet.service.MotionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetMotionHandler implements CommandHandler<SetMotion> {

  private final MotionService motionService;

  private SetMotionHandler(MotionService motionService) {
    this.motionService = motionService;
  }
  
  @Override
  public Response execute(SetMotion command) {
    try {
      log.debug("Got Set Motion Command!");
      motionService.setMotion(command.getThrustVectors());

      return new SetMotionResponse(true);
    } catch (Exception e) {
      log.error("Error setting motor power levels", e);
      return new SetMotionResponse(false, e);
    }
  }

  @Override
  public Class<SetMotion> getCommandType() {
    return SetMotion.class;
  }
}
