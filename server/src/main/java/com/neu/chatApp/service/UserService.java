package com.neu.chatApp.service;

import com.neu.chatApp.db.DB;
import com.neu.chatApp.db.MemoryDB;
import com.neu.chatApp.entity.User;
import logger.SimpleLogger;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private DB db;
  private MemoryDB memory;

  public UserService(DB db) {
    this.db = db;
    this.memory = new MemoryDB(db);
  }

  public ResponseEntity<String> login(String username, String password) {
    User user = db.select(username);
    if (user != null && user.getPassword().equals(password)) {
      db.updateOnlineStatus(username, true);
      memory.onlineUsers.add(username);
      SimpleLogger.info("Sign in success");
      return ResponseEntity.ok("Sign in success");
    }
    return ResponseEntity.badRequest().body("Sign in failed");
  }

  public ResponseEntity<String> signup(String username, String password) {
    // find if username already exists
    if (db.select(username) != null) {
      SimpleLogger.error("Sign up failed: Username already exists");
      return ResponseEntity.badRequest().body("Sign up failed: Username already exists");
    }
    db.insert(new User(username, password));
    return ResponseEntity.ok("Sign up success");
  }

  public ResponseEntity<String> logout(String username) {
    db.updateOnlineStatus(username, false);
    memory.onlineUsers.remove(username);
    SimpleLogger.info(username + " logged out");
    return ResponseEntity.ok("Logout success");
  }
}
