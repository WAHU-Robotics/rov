/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import me.jbuelow.rov.common.command.OpenVideo;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.VideoStreamAddress;
import me.jbuelow.rov.wet.service.CommandHandler;
import org.springframework.stereotype.Service;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
//@Slf4j
public class OpenVideoHandler implements CommandHandler<OpenVideo> {

  /* (non-Javadoc)
   * @see me.jbuelow.rov.service.CommandHandler#execute(Command)
   */
  @Override
  public Response execute(OpenVideo command) {
    new NativeDiscovery().discover();
    int port = 3621;

    String media;
    boolean windows= false;
    if (System.getProperty("os.name").toLowerCase().contains("win")) {
      //We are running windows
      windows=true;
      media = "dshow://";
    } else {
      //We are running linux
      media = "v4l2:///dev/video0";
    }
    //TODO fix this
    /*
    String[] options = {":sout=#duplicate{dst=rtp{sdp=rtsp://:" + port + "/stream}}",
        ":sout-all", ":sout-keep"};
    MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
    HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
    mediaPlayer.playMedia(media, options);
    */
    Process process = null;
    if (!windows) {
      try {
        process = Runtime.getRuntime()
            .exec("bash \"raspivid -t 0 -w 1280 -h 720 -br 50 -vf -o - | nc -lkv4" + port + "\"");
      } catch (IOException ignored) {
      }
    }

    InetAddress IP=null;
    try {
       IP=InetAddress.getLocalHost();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }

    String ip = IP.toString();
    VideoStreamAddress response = new VideoStreamAddress(ip+":"+port);
    //VideoStreamAddress response = new VideoStreamAddress("rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov");

    return response;
  }

  /* (non-Javadoc)
   * @see me.jbuelow.rov.service.CommandHandler#getCommandType()
   */
  @Override
  public Class<OpenVideo> getCommandType() {
    return OpenVideo.class;
  }

}
