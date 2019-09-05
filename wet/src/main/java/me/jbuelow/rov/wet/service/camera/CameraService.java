package me.jbuelow.rov.wet.service.camera;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.net.InetSocketAddress;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.wet.service.camera.agent.impl.ServerAgent;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CameraService {
  //TODO make start a server for many cameras

  //TODO make these values configurable from the config file
  private Webcam webcam = Webcam.getDefault();
  private Dimension resolution = new Dimension(1280, 720);
  private int fps = 30;
  private int bitrate = 12000000;
  private int quality = 10;
  private boolean quick = true;


  @PostConstruct
  public void initialize() {
    log.debug("Starting camera module for {}", webcam);
    webcam.setCustomViewSizes(resolution);
    webcam.setViewSize(resolution);

    ServerAgent agent = new ServerAgent(webcam, resolution, fps, bitrate, quality, quick);
    agent.start(new InetSocketAddress("localhost", 20000)); //TODO make port a part of application.yml config
  }
}
