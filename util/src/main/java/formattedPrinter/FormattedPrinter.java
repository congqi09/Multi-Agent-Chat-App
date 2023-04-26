package formattedPrinter;


/**
 * Print message in format.
 */
public class FormattedPrinter {

  /**
   * Print the title of a menu with a 100 length string, if the string is less than the length will be filled with "=".
   *
   * @param title the title to be printed
   */
  public static void printTitle(String title) {
    // wrap the title by two spaces
    String wrappedTitle = " " + title + " ";
    int length = wrappedTitle.length();
    String result;
    if (length < 100) {
      // filled the rest spots with "="
      int restSpots = 100 - length;
      int half = restSpots / 2;
      result = "=".repeat(half) + wrappedTitle + "=".repeat(restSpots - half);
    } else {
      result = wrappedTitle;
    }
    System.out.println(result);
  }

  /**
   * Print the end of the title.
   */
  public static void printEnd() {
    System.out.println("=".repeat(100));
  }

  /**
   * Print the line breaker.
   */
  public static void printLineBreaker() {
    System.out.println("-".repeat(100));
  }

  /**
   * Print client system message to user.
   *
   * @param msg message
   */
  public static void printSystemMessage(String msg) {
    System.out.println("[System message]: " + msg);
  }

  /**
   * Format the message to a standard send and receive message format.
   *
   * @param type    false: you -> someone; true: someone -> you
   * @param id      the id of the message sender / receiver
   * @param name    the name of the message sender / receiver
   * @param message the message content
   * @return the formatted string
   */
  public static String formatter(boolean type, Long id, String name, String message) {
    return type ? "[" + id + "] " + name + " -> " + "[You]: " + message : "[You] -> [" + id + "] " + name + ": " + message;
  }

  /**
   * Format the message to a standard response format.
   * broadcast message
   *
   * @param type    false: you -> all; true: all -> you
   * @param message the message
   * @return the formatted string
   */
  public static String formatter(boolean type, String message) {
    return type ? "[Broadcast] -> [You]: " + message : "[You] -> [Broadcast]: " + message;
  }


}
