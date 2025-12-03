package com.ourblogs.ui.gui;

import com.ourblogs.entity.Account;
import com.ourblogs.service.Service;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
  private MainFrame mainFrame;
  private Service service;
  private JTextField emailField;
  private JPasswordField passwordField;

  public LoginPanel(MainFrame mainFrame, Service service) {
    this.mainFrame = mainFrame;
    this.service = service;
    initializeUI();
  }

  private void initializeUI() {
    setLayout(new BorderLayout());
    setBackground(Color.WHITE);

    // Header
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(70, 130, 180));
    headerPanel.setPreferredSize(new Dimension(0, 80));
    JLabel titleLabel = new JLabel("Login to OurBlogs");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
    titleLabel.setForeground(Color.WHITE);
    headerPanel.add(titleLabel);
    add(headerPanel, BorderLayout.NORTH);

    // Center form panel
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(Color.WHITE);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Email label and field
    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0.3;
    formPanel.add(emailLabel, gbc);

    emailField = new JTextField(20);
    emailField.setFont(new Font("Arial", Font.PLAIN, 14));
    emailField.setPreferredSize(new Dimension(250, 35));
    gbc.gridx = 1;
    gbc.weightx = 0.7;
    formPanel.add(emailField, gbc);

    // Password label and field
    JLabel passwordLabel = new JLabel("Password:");
    passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.3;
    formPanel.add(passwordLabel, gbc);

    passwordField = new JPasswordField(20);
    passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
    passwordField.setPreferredSize(new Dimension(250, 35));
    gbc.gridx = 1;
    gbc.weightx = 0.7;
    formPanel.add(passwordField, gbc);

    // Button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    buttonPanel.setBackground(Color.WHITE);

    JButton loginButton = new JButton("Login");
    loginButton.setFont(new Font("Arial", Font.BOLD, 16));
    loginButton.setPreferredSize(new Dimension(150, 40));
    loginButton.setBackground(new Color(70, 130, 180));
    loginButton.setForeground(Color.BLACK);
    loginButton.setFocusPainted(false);
    loginButton.addActionListener(e -> handleLogin());
    buttonPanel.add(loginButton);

    JButton backButton = new JButton("Back");
    backButton.setFont(new Font("Arial", Font.PLAIN, 16));
    backButton.setPreferredSize(new Dimension(150, 40));
    backButton.setForeground(Color.BLACK);
    backButton.addActionListener(e -> {
      clearFields();
      mainFrame.showPanel(MainFrame.WELCOME_PANEL);
    });
    buttonPanel.add(backButton);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    formPanel.add(buttonPanel, gbc);

    add(formPanel, BorderLayout.CENTER);
  }

  private void handleLogin() {
    String email = emailField.getText().trim();
    String password = new String(passwordField.getPassword());

    if (email.isEmpty() || password.isEmpty()) {
      JOptionPane.showMessageDialog(
          this,
          "Please enter both email and password",
          "Validation Error",
          JOptionPane.WARNING_MESSAGE
      );
      return;
    }

    Account account = new Account();
    account.setEmail(email);
    account.setPassword(password);

    boolean authenticated = service.authenticate(account);

    if (authenticated) {
      clearFields();
      mainFrame.loginSuccess(account);
    } else {
      JOptionPane.showMessageDialog(
          this,
          "Invalid email or password",
          "Login Failed",
          JOptionPane.ERROR_MESSAGE
      );
      passwordField.setText("");
    }
  }

  private void clearFields() {
    emailField.setText("");
    passwordField.setText("");
  }
}