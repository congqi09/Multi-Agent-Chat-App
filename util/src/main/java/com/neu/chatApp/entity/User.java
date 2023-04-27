package com.neu.chatApp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
  private String username;
  private String email;
  private String password;
  private String hostname;
  private int port;
  private boolean isOnline;

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.isOnline = false;
  }

  public User(String username, String password) {
    this(username, "", password);
  }
}
