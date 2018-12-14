/**
 *
 */
package me.jbuelow.rov.wet.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Brian Wachsmuth
 */
@Configuration
public class RovConfiguration {

  @Bean
  public ExecutorService executorService() {
    return Executors.newCachedThreadPool();
  }
}
