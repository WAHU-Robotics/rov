/**
 *
 */
package me.jbuelow.rov.wet.config;

import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import me.jbuelow.rov.wet.vehicle.hardware.PCA9685;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Configuration
public class RovConfiguration {

  @Bean
  public ExecutorService executorService() {
    return Executors.newCachedThreadPool();
  }

  @Bean
  public PCA9685 PCADriver() throws UnsupportedBusNumberException {
    return new PCA9685();
  }
}
