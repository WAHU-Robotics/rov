package me.jbuelow.rov.common.command;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.jbuelow.rov.common.capabilities.ThrustAxis;

@SuppressWarnings("DefaultAnnotationParam")
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class SetMotion extends Command {

  /**
   *
   */
  private static final long serialVersionUID = 3803795320272750470L;

  private Map<ThrustAxis, Integer> thrustVectors = new HashMap<>();
}