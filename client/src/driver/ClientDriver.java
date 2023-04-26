package driver;

import data.ClientData;
import gui.Chat;
import gui.Main;

public class ClientDriver {
    public ClientDriver(String hostname, int port) {
        ClientData.init(hostname, port);
        new Thread((Runnable) new Main()).start();
    }
}
