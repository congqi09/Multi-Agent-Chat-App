package api;

import java.rmi.Remote;
import java.util.List;

import entity.Message;

public interface Server extends Remote {
  boolean signUp(String username, String password) throws Exception;
  boolean fakeSignIn(String username, String password) throws Exception;
  boolean signIn(String username, String password) throws Exception;
  void send(Message msg) throws Exception;
  List<Message> get() throws Exception;
}
