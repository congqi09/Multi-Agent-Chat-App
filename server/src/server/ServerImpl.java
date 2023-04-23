package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import api.Server;
import entity.Message;

public class ServerImpl extends UnicastRemoteObject implements Server {
  private List<Message> messages;
  private List<Map<String, String>> users;

  protected ServerImpl() throws RemoteException {
    messages = new ArrayList<>();
    users = new ArrayList<>();
  }

  @Override
  public boolean signUp(String username, String password) throws Exception {
    users.add(Map.of(username, password));
    return true;
  }

  @Override
  public boolean fakeSignIn(String username, String password) throws Exception {
    return true;
  }

  @Override
  public boolean signIn(String username, String password) throws Exception {
    return users.contains(Map.of(username, password));
  }

  @Override
  public void send(Message msg) throws Exception {
    messages.add(msg);
  }

  @Override
  public List<Message> get() throws Exception {
    return messages;
  }
}
