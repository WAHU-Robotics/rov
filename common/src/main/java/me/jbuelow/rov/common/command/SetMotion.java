package me.jbuelow.rov.common.command;

import lombok.Getter;
import lombok.Setter;
import me.jbuelow.rov.common.object.MotionVector;

public class SetMotion extends Command {

  @Getter
  private MotionVector motionVector;

  public SetMotion(MotionVector motionVector) {
    this.motionVector = motionVector;
  }

}
