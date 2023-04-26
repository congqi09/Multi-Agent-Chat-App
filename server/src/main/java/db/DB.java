package db;

import entity.User;

public interface DB {
  void insert(User user);
  User select(String username);
}
