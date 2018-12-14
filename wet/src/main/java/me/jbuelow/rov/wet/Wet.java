package me.jbuelow.rov.wet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//TODO: java vlc embedded thingy
//this is good code.

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@SpringBootApplication
@EnableScheduling
public class Wet {

  public static void main(String[] args) {
    SpringApplication.run(Wet.class, args);
  }
}
