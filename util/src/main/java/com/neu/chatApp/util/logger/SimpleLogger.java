package com.neu.chatApp.util.logger;

import java.text.SimpleDateFormat;

/**
 * A simple logger.
 * Author: Cong Qi
 * Email: qi.co@northeastern.edu
 */
public class SimpleLogger {

  /**
   * Output an info message with a timestamp.
   * @param message the info message
   */
  public static void info(String message) {
    System.out.println(currentTime() + " INFO: " + message);
  }

  /**
   * Output an error message with a timestamp.
   * @param message the error message
   */
  public static void error(String message) {
    System.out.println(currentTime() + " ERROR: " + message);
  }

  private static String currentTime() {
    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    return timeFormat.format(System.currentTimeMillis());
  }
}
