package me.jbuelow.rov.dry.ui.video;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.dry.discovery.VehicleDiscoveryEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class VideoService {

  private final VideoFrameReceiver frameReceiver;
  private VideoTransport transport;

  @Value("${rov.camera.url}")
  private String videoUrl = "udp://239.0.0.1:1234";

  public VideoService(VideoFrameReceiver frameReceiver) {
    this.frameReceiver = frameReceiver;
  }
  
  @PostConstruct
  public void init() {
    log.info("Started camera service");
  }

  @EventListener
  @Order(1)
  public void initiateConnection(VehicleDiscoveryEvent event) {
    new Thread() {
      @Override
      public void run() {
        start();
      }
    };
  }

  private void start() {
    log.info("Starting camera stream...");
    transport = new VideoTransport(videoUrl, frameReceiver);
    transport.start();
  }

}
