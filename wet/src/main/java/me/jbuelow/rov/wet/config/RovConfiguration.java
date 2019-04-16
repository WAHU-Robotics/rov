package me.jbuelow.rov.wet.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Configuration
class RovConfiguration {

  @Bean
  public ExecutorService executorService() {
    return Executors.newCachedThreadPool();
  }
}
