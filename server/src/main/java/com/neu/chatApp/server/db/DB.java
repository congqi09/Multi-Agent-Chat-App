package com.neu.chatApp.server.db;

import com.neu.chatApp.util.entity.User;

public interface DB {
  void insert(User user);
  User select(String username);
}
