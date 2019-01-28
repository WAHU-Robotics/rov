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
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

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
    String media = "dshow://";
    String[] options = {":sout=#duplicate{dst=rtp{sdp=rtsp://:" + port + "/stream},dst=display}",
        ":sout-all", ":sout-keep"};
    MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
    HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
    mediaPlayer.playMedia(media, options);
    //VideoStreamAddress response = new VideoStreamAddress("rtsp:/" + command.address + ":1234");
    //VideoStreamAddress response = new VideoStreamAddress("rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov");
    VideoStreamAddress response = new VideoStreamAddress("rtsp://127.0.0.1:" + port + "/stream");

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
