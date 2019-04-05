/**
 *
 */
package me.jbuelow.rov.dry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Dry side configuration class for spring boot
 */
@Configuration
public class DryConfiguration {

  @Bean
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(10);
  }

}
