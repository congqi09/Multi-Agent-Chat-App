package db;

import java.util.List;

import entity.User;

public interface DB {
  void insert(User user);
  User select(String username);
  List<String> getOnlineUsers();
  void updateOnlineStatus(String username, boolean isOnline);
  void updateHostnameAndPort(String username, String hostname, int port);
}
