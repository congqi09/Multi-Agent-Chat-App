package com.neu.chatApp.server.service;

import com.neu.chatApp.server.db.DB;

public class UserService {
  private final DB db;

  public UserService(DB db) {
    this.db = db;
  }
}
