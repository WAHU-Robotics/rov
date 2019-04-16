package me.jbuelow.rov.dry.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Dry side configuration class for spring boot
 */
@Configuration
class DryConfiguration {

  @Bean
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(10);
  }

}
