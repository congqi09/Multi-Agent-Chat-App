package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class ChatAPP {
  private JPanel panelMain;
  private JList chatHistory;
  private JTextPane newMessage;
  private JButton sendButton;
  private final List<String> messageHistory = new ArrayList<>();

  public ChatAPP() {
    sendButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        messageHistory.add(newMessage.getText());
        newMessage.setText(null); // clear the input field
        chatHistory.setListData(messageHistory.toArray());
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
