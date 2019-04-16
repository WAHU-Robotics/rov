package me.jbuelow.rov.wet.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.command.SetServo;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.SetMotionResponse;
import me.jbuelow.rov.common.response.SetServoResponse;
import me.jbuelow.rov.wet.service.CommandHandler;
import me.jbuelow.rov.wet.service.ServoService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetServoHandler implements CommandHandler<SetServo> {

  private final ServoService servoService;

  private SetServoHandler(ServoService servoService) {
    this.servoService = servoService;
  }
  
  @Override
  public Response execute(SetServo command) {
    try {
      log.debug("Got Set Motion Command!");
      servoService.setValues(command.getServoValues());

      return new SetServoResponse(true);
    } catch (Exception e) {
      log.error("Error setting servo power levels", e);
      return new SetMotionResponse(false, e);
    }
  }

  @Override
  public Class<SetServo> getCommandType() {
    return SetServo.class;
  }
}
