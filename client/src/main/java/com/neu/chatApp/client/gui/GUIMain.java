package com.neu.chatApp.client.gui;

import java.awt.*;

import javax.swing.*;

import com.neu.chatApp.client.data.ClientData;
import com.neu.chatApp.client.rest.RestClient;
import com.neu.chatApp.entity.Message;

import okhttp3.Response;

public class GUIMain {
  private static String username;
  private static String password;
  private RestClient restClient;

  public GUIMain() {
    // TODO: initialize these in command line args?
    String hostname = "localhost";
    int port = 8080;
    ClientData.serverHostname = hostname;
    ClientData.serverHttpPort = port;
    ClientData.baseURL = "http://" + hostname + ":" + port + "/api/user";
    restClient = new RestClient(ClientData.baseURL);

    JFrame frame =  new JFrame("Main");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 400);
    Container contentPane = frame.getContentPane();
    CardLayout cardLayout = new CardLayout();
    contentPane.setLayout(cardLayout);

    Log login = new Log();
    Chat chat = new Chat();
    contentPane.add(login.panelMain, "login");
    contentPane.add(chat.panelMain, "chat");

    login.signup.addActionListener(e -> {
      username = login.usernameField.getText();
      password = login.passwordField.getText();
      if (password.length() < 4) {
        JOptionPane.showMessageDialog(login.signup, "password must be at least 4 characters long, please enter again");
        login.usernameField.setText(null);
        login.passwordField.setText(null);
        return;
      }
      JOptionPane.showMessageDialog(login.signup, "you have successfully signed up, click the signin button to continue.");

      login.usernameField.setText(null);
      login.passwordField.setText(null);
      Response response;
      response = restClient.signUp(username, password);

    });

    login.loginButton.addActionListener(e -> {
      username = login.usernameField.getText();
      password = login.passwordField.getText();
      cardLayout.show(contentPane, "chat");
      chat.currentUser.setText("Current User: " + username);
      Response response;
      response = restClient.login(username, password);

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

    chat.logoutButton.addActionListener(e -> {
      int input = JOptionPane.showConfirmDialog(chat.logoutButton, "Log out?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
      if (input == 0) { /// 0=ok, 2=cancel
        Response response;
        response = restClient.logout(username);
        frame.dispose();
      }
    });


    frame.setVisible(true);
  }

  public static void main(String[] args) {
    new GUIMain();
  }
}
