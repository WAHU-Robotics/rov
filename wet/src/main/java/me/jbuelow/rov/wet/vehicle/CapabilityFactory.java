package me.jbuelow.rov.wet.vehicle;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.jbuelow.rov.common.capabilities.AbstractCapability;
import me.jbuelow.rov.common.capabilities.Capability;
import me.jbuelow.rov.common.capabilities.Motor;
import me.jbuelow.rov.common.capabilities.Servo;
import me.jbuelow.rov.common.capabilities.Video;
import org.springframework.beans.BeanUtils;

/**
 * Generates a Capability Object based on a Configuration Object
 */
public abstract class CapabilityFactory {

  private static final Map<Class<? extends AccessoryConfig>, Class<? extends AbstractCapability>> clazzMatrix = new HashMap<>();

  static {
    clazzMatrix.put(MotorConfig.class, Motor.class);
    clazzMatrix.put(ServoConfig.class, Servo.class);
    clazzMatrix.put(VideoConfig.class, Video.class);
  }

  public static Capability getCapability(AccessoryConfig config) {
    Class<? extends AbstractCapability> capabilityClazz = clazzMatrix.get(config.getClass());

    if (capabilityClazz == null) {
      throw new IllegalArgumentException(
          "Unable to convert configuration class '" + config.getClass().getName()
              + "' to a capability.");
    }

    return generateCapability(capabilityClazz, config);
  }
  
  private static <T extends AbstractCapability> T generateCapability(Class<T> clazz, AccessoryConfig config) {
    Constructor<T> ctor;
    
    try {
      ctor = clazz.getConstructor(UUID.class);
    } catch (NoSuchMethodException | SecurityException e) {
      throw new RuntimeException("Error generating Vehicle Capability", e);
    }
    
    T capability = BeanUtils.instantiateClass(ctor, config.getId());
    BeanUtils.copyProperties(config, capability);
    
    return capability;
  }
}
