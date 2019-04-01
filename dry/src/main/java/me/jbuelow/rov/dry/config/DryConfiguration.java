/**
 *
 */
package me.jbuelow.rov.dry.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import me.jbuelow.rov.dry.ui.setup.ConnectionIdler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Configuration
public class DryConfiguration {

  @Bean
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(10);
  }

  @Bean
  public ConnectionIdler connectionIdler() {
    return new ConnectionIdler();
  }

}
