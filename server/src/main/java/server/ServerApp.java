package server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import api.RMIServer;
import service.RMIServerImpl;

public class ServerApp {
  public static void main(String[] args) {
    try {
      LocateRegistry.createRegistry(6666);
      RMIServer server = new RMIServerImpl();
      Naming.bind("rmi://localhost:6666/api.Server", server);
    } catch (MalformedURLException | AlreadyBoundException | RemoteException e) {
      throw new RuntimeException(e);
    }
  }
}
