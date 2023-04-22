package entity;

import java.io.Serializable;

public class User implements Serializable {
  private String username;
  private String password;

  public User(String user, String pass) {
    username = user;
    password = pass;
  }
}
