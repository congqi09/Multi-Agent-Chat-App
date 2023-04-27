package com.neu.chatApp.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Message {
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
