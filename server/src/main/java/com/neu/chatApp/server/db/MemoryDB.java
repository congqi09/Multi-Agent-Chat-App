package com.neu.chatApp.server.db;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoryDB {
  public List<String> onlineUsers;

  public MemoryDB(DB db) {
    this.onlineUsers = db.getOnlineUsers();
    // TODO: try to ping each online user and update online status

  }
}
