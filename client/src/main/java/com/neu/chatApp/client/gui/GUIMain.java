package com.neu.chatApp.client.gui;

import java.awt.*;

import javax.swing.*;

import com.neu.chatApp.entity.Message;

public class GUIMain {
  private static String username;

  public GUIMain() {
    JFrame frame = new JFrame("Main");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 400);
    Container contentPane = frame.getContentPane();
    CardLayout cardLayout = new CardLayout();
    contentPane.setLayout(cardLayout);

    com.neu.chatApp.client.gui.Login login = new Login();
    Chat chat = new Chat();
    contentPane.add(login.panelMain, "login");
    contentPane.add(chat.panelMain, "chat");

    login.loginButton.addActionListener(e -> {
      username = login.usernameField.getText();
      cardLayout.show(contentPane, "chat");
      chat.currentUser.setText("Current User: " + username);
    });

    chat.sendButton.addActionListener(e -> {
      Message message = new Message(username, chat.newMessage.getText());
      try {
        chat.server.send(message);
        chat.messageHistory.clear();
        chat.messageHistory.addAll(chat.server.get().stream().map(Message::toString).toList());
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
      chat.chatHistory.setListData(chat.messageHistory.toArray());
      chat.newMessage.setText(null); // clear the input field
    });

    frame.setVisible(true);
  }
}
