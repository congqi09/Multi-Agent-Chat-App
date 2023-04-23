package gui;

import java.awt.*;

import javax.swing.*;

public class Main {
  private static String username;

  public static void main(String[] args) {
    JFrame frame = new JFrame("Main");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 400);
    Container contentPane = frame.getContentPane();
    CardLayout cardLayout = new CardLayout();
    contentPane.setLayout(cardLayout);

    Login login = new Login();
    Chat chat = new Chat();
    contentPane.add(login.panelMain, "login");
    contentPane.add(chat.panelMain, "chat");

    login.loginButton.addActionListener(e -> {
      username = login.usernameField.getText();
      cardLayout.show(contentPane, "chat");
      chat.currentUser.setText("Current User: " + username);
    });

    frame.setVisible(true);
  }
}
