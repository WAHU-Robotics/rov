/**
 *
 */
package me.jbuelow.rov.wet.service.impl;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import me.jbuelow.rov.common.OpenVideo;
import me.jbuelow.rov.common.Response;
import me.jbuelow.rov.common.VideoStreamAddress;
import me.jbuelow.rov.wet.service.CommandHandler;
import org.springframework.stereotype.Service;

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
    try {
      Runtime.getRuntime().exec(
          "raspivid -o - -t 9999999 |cvlc -vvv stream:///dev/stdin --sout '#rtp{sdp=rtsp://:" + 1234
              + "/}' :demux=h264");
    } catch (IOException e) {
      log.error("Could not start raspivid server as requested.");
      //e.printStackTrace();
    }
    return new VideoStreamAddress("rtsp:/" + command.address + ":1234");
  }

  /* (non-Javadoc)
   * @see me.jbuelow.rov.service.CommandHandler#getCommandType()
   */
  @Override
  public Class<OpenVideo> getCommandType() {
    return OpenVideo.class;
  }

}
