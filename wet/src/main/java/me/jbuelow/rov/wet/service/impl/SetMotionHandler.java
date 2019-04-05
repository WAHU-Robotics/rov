package me.jbuelow.rov.wet.service.impl;

import me.jbuelow.rov.common.command.SetMotion;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.wet.service.CommandHandler;
import me.jbuelow.rov.wet.service.MotionService;
import org.springframework.stereotype.Service;

@Service
public class SetMotionHandler implements CommandHandler<SetMotion> {
  private MotionService motionService;
  
  public SetMotionHandler(MotionService motionService) {
    this.motionService = motionService;
  }
  
  @Override
  public Response execute(SetMotion command) {
    // TODO Auto-generated method stub
    motionService.setMotion(command.getThrustVectors());
    
    return null;
  }

  @Override
  public Class<SetMotion> getCommandType() {
    return SetMotion.class;
  }
}
