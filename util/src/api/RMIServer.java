package api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.Message;

public interface RMIServer extends Remote {
  boolean signUp(String username, String password) throws RemoteException;
  boolean signIn(String username, String password) throws RemoteException;
  void send(Message msg) throws RemoteException;
  List<Message> get() throws RemoteException;
}
