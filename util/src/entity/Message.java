package entity;

import java.io.Serializable;

public class Message implements Serializable {
  User user;
  String content;

  public Message(User user, String text) {
    this.user = user;
    this.content = text;
  }
}
