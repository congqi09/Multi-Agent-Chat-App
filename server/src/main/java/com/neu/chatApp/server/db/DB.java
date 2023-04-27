package com.neu.chatApp.server.db;

import java.util.List;

import com.neu.chatApp.entity.User;
import com.neu.chatApp.entity.Message;

public interface DB {
  void insertUser(User user);
  User select(String username);
  List<String> getOnlineUsers();
  void updateOnlineStatus(String username, boolean isOnline);
  void updateHostnameAndPort(String username, String hostname, int port);
  List<Message> getMessages();
  void insertMessage(Message msg);
}
