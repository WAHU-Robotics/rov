package me.jbuelow.rov.wet.service.camera;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Handles opening and reading from the camera and streaming it to a UDP multicast using javacv/ffmpeg
 *
 * @author Jacob Buelow
 */
@Slf4j
@Service
public class WebCam {

  final int width = 1280;
  final int height = 720;

  private FrameGrabber grabber;
  private FFmpegFrameRecorder recorder;
  private StreamThread thread;

  @PostConstruct
  public void start() {
    log.info("Starting camera stream...");

    grabber = new OpenCVFrameGrabber(0);
    grabber.setImageHeight(height);
    grabber.setImageWidth(width);
    try {
      grabber.start();
    } catch (java.lang.Exception e) {
      log.error("Failed to start camera stream!", e);
      return;
    }
    log.debug("Opened camera frame grabber");

    recorder = new FFmpegFrameRecorder("udp://239.0.0.1:1234?ttl=130&pkt_size=1316", width, height);
    recorder.setVideoCodecName("copy");
    recorder.setFormat("mpegts");
    recorder.setOption("tune", "zerolatency");
    recorder.setOption("fflags", "nobuffer");
    recorder.setOption("flags", "low_delay");
    recorder.setFrameRate(30);
    recorder.setVideoBitrate(100000000);
    try {
      recorder.start();
    } catch (java.lang.Exception e) {
      log.error("Failed to start camera stream!", e);
      return;
    }
    log.debug("Opened stream frame recorder");

    thread = new StreamThread();

    thread.start();
    log.info("Camera stream started!");
  }

  /**
   * Helper thread class to loop the process of grabbing frames and handing them off to ffmpeg to stream them
   */
  private class StreamThread extends Thread {

    @Override
    public void run() {
      Frame image = null;
      try {
        image = grabber.grab();
      } catch (Exception e) {
        log.error("Error grabbing initial frame!", e);
      }

      while (true) {
        try {
          if ((image = grabber.grab()) == null) break;
        } catch (Exception e) {
          log.error("Error grabbing frame!", e);
        }
        try {
          recorder.record(image);
        } catch (FrameRecorder.Exception e) {
          log.error("Error recording frame!", e);
        }
      }

      try {
        recorder.stop();
        grabber.stop();
      } catch (Exception | FrameRecorder.Exception e) {
        log.error("Error occurred while stopping stream:", e);
      }
    }

  }
}
