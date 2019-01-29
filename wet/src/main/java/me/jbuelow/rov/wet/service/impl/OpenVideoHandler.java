/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.OpenVideo;
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.common.VideoStreamAddress;
import me.jbuelow.rov.wet.service.CommandHandler;
import org.springframework.stereotype.Service;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
@Slf4j
public class OpenVideoHandler implements CommandHandler<OpenVideo> {

  /* (non-Javadoc)
   * @see me.jbuelow.rov.service.CommandHandler#execute(Command)
   */
  @Override
  public Response execute(OpenVideo command) {
    new NativeDiscovery().discover();
    int port = 3621;

    String media;
    if (System.getProperty("os.name").toLowerCase().contains("win")) {
      //We are running windows
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

    VideoStreamAddress response = new VideoStreamAddress("dshow://");
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
