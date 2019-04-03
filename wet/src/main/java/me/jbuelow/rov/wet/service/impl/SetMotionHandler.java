package me.jbuelow.rov.wet.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.command.SetMotion;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.SetMotionResponse;
import me.jbuelow.rov.wet.service.CommandHandler;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
@Slf4j
public class SetMotionHandler implements CommandHandler<SetMotion> {

  @Override
  public Response execute(SetMotion command) {
    return new SetMotionResponse(false, new NotImplementedException());
  }

  @Override
  public Class<SetMotion> getCommandType() {
    return SetMotion.class;
  }

}
