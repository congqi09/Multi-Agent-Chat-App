package com.neu.chatApp.client.gui;

import com.google.gson.Gson;

import java.awt.*;
import javax.swing.*;

import com.neu.chatApp.client.data.ClientData;
import com.neu.chatApp.client.rest.RestClient;
import com.neu.chatApp.entity.Message;

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

    Login login = new Login();
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
      login.usernameField.setText(null);
      login.passwordField.setText(null);
      String response = restClient.signUp(username, password);
      if (response != null) {
        JOptionPane.showMessageDialog(login.signup, "you have successfully signed up, click the login button to continue.");
      } else {
        JOptionPane.showMessageDialog(login.signup, "username already exists, please enter again");
        login.usernameField.setText(null);
        login.passwordField.setText(null);
      }
    });

    login.loginButton.addActionListener(e -> {
      username = login.usernameField.getText();
      password = login.passwordField.getText();

      String response = restClient.login(username, password);

      if (response != null) {
        chat.currentUser.setText("Current User: " + username);
        response = restClient.getMessages();
        cardLayout.show(contentPane, "chat");
        if (response != null) {
          Message[] messages = new Gson().fromJson(response, Message[].class);
          for (Message message : messages) {
            System.out.println(message);
            chat.messageHistory.add(message.toString());
          }
          chat.chatHistory.setListData(chat.messageHistory.toArray());
        }
      } else {
        JOptionPane.showMessageDialog(login.loginButton, "username or password is incorrect, please try again");
        login.usernameField.setText(null);
        login.passwordField.setText(null);
      }
    });

    chat.sendButton.addActionListener(e -> {
      restClient.sendMessage(username, chat.newMessage.getText());
      chat.messageHistory.add(username + ": " + chat.newMessage.getText());
      chat.chatHistory.setListData(chat.messageHistory.toArray());
      // TODO: update message realtime
      chat.newMessage.setText(null); // clear the input field
    });

    chat.logoutButton.addActionListener(e -> {
      int input = JOptionPane.showConfirmDialog(chat.logoutButton, "Log out?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
      if (input == 0) { /// 0=ok, 2=cancel
        restClient.logout(username);
        frame.dispose();
      }
    });


    frame.setVisible(true);
  }

  public static void main(String[] args) {
    new GUIMain();
  }
}
