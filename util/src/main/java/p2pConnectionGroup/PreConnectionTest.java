package p2pConnectionGroup;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

@Slf4j
public class PreConnectionTest {

  private static final int MIN_PORT_NUMBER = 1;

  private static final int MAX_PORT_NUMBER = 65535;

  /**
   * To test the given port is available for the system to run.
   *
   * @return true if the port is available, otherwise false
   */
  public static boolean testPortAvailable(int port) {
    if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
      throw new IllegalArgumentException("Invalid start port: " + port);
    }
    ServerSocket ss = null;
    DatagramSocket ds = null;
    try {
      // test if the port can be used for tcp and udp
      ss = new ServerSocket(port);
      ss.setReuseAddress(true);
      ds = new DatagramSocket(port);
      ds.setReuseAddress(true);
      return true;
    } catch (IOException e) {
      log.error("Unable to start the application, reason: " + e.getMessage());
    } finally {
      if (ds != null) {
        ds.close();
      }
      if (ss != null) {
        try {
          ss.close();
        } catch (IOException e) {
          /* should not be thrown */
        }
      }
    }
    return false;
  }

}
