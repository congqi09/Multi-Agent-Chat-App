package gui;

import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import api.Server;

public class Chat {
  JPanel panelMain;
  JList chatHistory;
  JTextPane newMessage;
  JButton sendButton;
  JLabel currentUser;
  Server server;
  final List<String> messageHistory = new ArrayList<>();

  public Chat() {
    try {
      Registry registry = LocateRegistry.getRegistry("localhost", 6666);
      server = (Server) registry.lookup("api.Server");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Chat");
    frame.setContentPane(new Chat().panelMain);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}
