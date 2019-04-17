package me.jbuelow.rov.wet.vehicle;

/* This file is part of WAHU ROV Software.
 *
 * WAHU ROV Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WAHU ROV Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WAHU ROV Software.  If not, see <https://www.gnu.org/licenses/>.
 */

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

  private static Map<Class<? extends AccessoryConfig>, Class<? extends AbstractCapability>> clazzMatrix = new HashMap<>();

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
