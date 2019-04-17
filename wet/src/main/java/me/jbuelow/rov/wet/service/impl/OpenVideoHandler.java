package me.jbuelow.rov.wet.service.impl;

/* This file is part of WAHU ROV Software.
 *
 * WAHU ROV Software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WAHU ROV Software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WAHU ROV Software.  If not, see <https://www.gnu.org/licenses/>.
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import me.jbuelow.rov.common.command.OpenVideo;
import me.jbuelow.rov.common.response.Response;
import me.jbuelow.rov.common.response.VideoStreamAddress;
import me.jbuelow.rov.wet.service.CommandHandler;
import org.springframework.stereotype.Service;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
@Service
//@Slf4j
public class OpenVideoHandler implements CommandHandler<OpenVideo> {


  /**
   * Handles the OpenVideo command
   *
   * @param command OpenVideo instance
   * @return VideoStreamAddress instance
   */
  @Override
  public Response execute(OpenVideo command) {
//    new NativeDiscovery().discover();
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
    VideoStreamAddress response = new VideoStreamAddress(ip + ":" + port);
    //VideoStreamAddress response = new VideoStreamAddress("rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov");

    return response;
  }


  /**
   * Gives the class of the command this handler is supposed to handle
   *
   * @return class instance of OpenVideo
   */
  @Override
  public Class<OpenVideo> getCommandType() {
    return OpenVideo.class;
  }

}
