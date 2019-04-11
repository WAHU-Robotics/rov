package me.jbuelow.rov.common.command;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.jbuelow.rov.common.capabilities.Tool;

@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class SetServo extends Command {

  private static final long serialVersionUID = 5535206604137082504L;
  private Map<Tool, Integer> servoValues = new HashMap<>();
}
