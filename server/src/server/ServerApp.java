package server;

import org.springframework

public class ServerApp implements CommandLineRunner {
  public static void main(String[] args) {
    try {
      LocateRegistry.createRegistry(6666);
      Server server = new ServerImpl();
      Naming.bind("rmi://localhost:6666/api.Server", server);
    } catch (MalformedURLException | AlreadyBoundException | RemoteException e) {
      throw new RuntimeException(e);
    }
  }
}
