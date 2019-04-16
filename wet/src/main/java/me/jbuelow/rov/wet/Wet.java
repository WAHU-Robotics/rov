package me.jbuelow.rov.wet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@SpringBootApplication
@EnableScheduling
class Wet {

  public static void main(String[] args) {
    SpringApplication.run(Wet.class, args);
  }
}
