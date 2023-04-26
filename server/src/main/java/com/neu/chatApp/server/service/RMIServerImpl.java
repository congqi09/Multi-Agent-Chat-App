package com.neu.chatApp.server.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.neu.chatApp.util.api.RMIServer;
import com.neu.chatApp.server.db.DB;
import com.neu.chatApp.server.db.MongoDB;
import com.neu.chatApp.util.entity.Message;
import com.neu.chatApp.util.entity.User;
import com.neu.chatApp.util.logger.SimpleLogger;

public class RMIServerImpl extends UnicastRemoteObject implements RMIServer {
  private DB db;

  public RMIServerImpl() throws RemoteException {
    db = new MongoDB();
  }

  @Override
  public boolean signUp(String username, String password) {
    // find if username already exists
    if (db.select(username) != null) {
      SimpleLogger.error("Sign up failed: Username already exists");
      return false;
    }
    db.insert(new User(username, password));
    return true;
  }

  @Override
  public boolean signIn(String username, String password) {
    User user = db.select(username);
    if (user != null && user.getPassword().equals(password)) {
      SimpleLogger.info("Sign in success");
      return true;
    }
    return false;
  }

  @Override
  public void send(Message msg) {
    System.out.println("Server received message: " + msg);
//    messages.add(msg);
  }

  @Override
  public List<Message> get() {
//    return messages;
    return null;
  }
}
