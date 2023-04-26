package com.neu.chatApp.server.service;

import com.neu.chatApp.server.db.DB;
import com.neu.chatApp.util.entity.User;
import com.neu.chatApp.util.logger.SimpleLogger;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  private DB db;

  public UserService(DB db) {
    this.db = db;
  }

  public boolean login(String username, String password) {
    User user = db.select(username);
    if (user != null && user.getPassword().equals(password)) {
      SimpleLogger.info("Sign in success");
      return true;
    }
    return false;
  }

  public boolean signup(String username, String password) {
    // find if username already exists
    if (db.select(username) != null) {
      SimpleLogger.error("Sign up failed: Username already exists");
      return false;
    }
    db.insert(new User(username, password));
    return true;
  }
}
