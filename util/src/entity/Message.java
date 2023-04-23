package entity;

import java.io.Serializable;

public class Message implements Serializable {
  String username;
  String content;

  public Message(String username, String text) {
    this.username = username;
    this.content = text;
  }

  @Override
  public String toString() {
    return username + ": " + content;
  }
}
