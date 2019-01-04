/**
 *
 */
package me.jbuelow.rov.common;

import java.util.Map;
import java.util.Properties;
import lombok.Setter;

/**
 * @author Jacob Buelow
 * @author Brian Wachsmuth
 */
public class SystemStats extends Response {

  @Setter
  private Map<String, String> environment = null;

  @Setter
  private Properties properties = null;

}
