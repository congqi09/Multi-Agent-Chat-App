package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import api.Server;
import entity.Message;
import entity.User;

public class ChatAPP {
  private JPanel panelMain;
  private JList chatHistory;
  private JTextPane newMessage;
  private JButton sendButton;
  private Server server;
  private final List<String> messageHistory = new ArrayList<>();

  public ChatAPP() {
    try {
      Registry registry = LocateRegistry.getRegistry("localhost", 6666);
      server = (Server) registry.lookup("api.Server");
    } catch (Exception e) {
      e.printStackTrace();
    }

    sendButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          server.send(new Message(new User("user", ""), newMessage.getText()));
        } catch (Exception ex) {
          throw new RuntimeException(ex);
        }
        messageHistory.add(newMessage.getText());
        newMessage.setText(null); // clear the input field
        chatHistory.setListData(messageHistory.toArray());
        // TODO： get messages from server or other clients
      }
    });
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("ChatAPP");
    frame.setContentPane(new ChatAPP().panelMain);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}
