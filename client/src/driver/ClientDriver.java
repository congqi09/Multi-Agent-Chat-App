package driver;

public class ClientDriver {
    public ClientDriver(String hostnmae, int port) {
        ClientData.init(hostname, port);
        new Thread(new UI()).start();
    }
}
