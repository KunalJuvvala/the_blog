package com.ourblogs.ui.gui;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blogger;
import com.ourblogs.service.Service;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
  private MainFrame mainFrame;
  private Service service;
  private JTextField emailField;
  private JPasswordField passwordField;
  private JPasswordField confirmPasswordField;
  private JTextField nameField;
  private JRadioButton publicRadio;
  private JRadioButton privateRadio;

  public RegisterPanel(MainFrame mainFrame, Service service) {
    this.mainFrame = mainFrame;
    this.service = service;
    initializeUI();
  }

  private void initializeUI() {
    setLayout(new BorderLayout());
    setBackground(Color.WHITE);

    // Header
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(60, 179, 113));
    headerPanel.setPreferredSize(new Dimension(0, 80));
    JLabel titleLabel = new JLabel("Create New Account");
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

    // Email
    addFormField(formPanel, gbc, "Email:", 0);
    emailField = new JTextField(20);
    emailField.setFont(new Font("Arial", Font.PLAIN, 14));
    emailField.setPreferredSize(new Dimension(250, 35));
    gbc.gridx = 1;
    gbc.gridy = 0;
    formPanel.add(emailField, gbc);

    // Password
    addFormField(formPanel, gbc, "Password:", 1);
    passwordField = new JPasswordField(20);
    passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
    passwordField.setPreferredSize(new Dimension(250, 35));
    gbc.gridx = 1;
    gbc.gridy = 1;
    formPanel.add(passwordField, gbc);

    // Confirm Password
    addFormField(formPanel, gbc, "Confirm Password:", 2);
    confirmPasswordField = new JPasswordField(20);
    confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
    confirmPasswordField.setPreferredSize(new Dimension(250, 35));
    gbc.gridx = 1;
    gbc.gridy = 2;
    formPanel.add(confirmPasswordField, gbc);

    // Name
    addFormField(formPanel, gbc, "Full Name:", 3);
    nameField = new JTextField(20);
    nameField.setFont(new Font("Arial", Font.PLAIN, 14));
    nameField.setPreferredSize(new Dimension(250, 35));
    gbc.gridx = 1;
    gbc.gridy = 3;
    formPanel.add(nameField, gbc);

    // Privacy Setting
    JLabel privacyLabel = new JLabel("Account Type:");
    privacyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridx = 0;
    gbc.gridy = 4;
    formPanel.add(privacyLabel, gbc);

    JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    radioPanel.setBackground(Color.WHITE);
    publicRadio = new JRadioButton("Public");
    publicRadio.setFont(new Font("Arial", Font.PLAIN, 14));
    publicRadio.setSelected(true);
    privateRadio = new JRadioButton("Private");
    privateRadio.setFont(new Font("Arial", Font.PLAIN, 14));
    ButtonGroup group = new ButtonGroup();
    group.add(publicRadio);
    group.add(privateRadio);
    radioPanel.add(publicRadio);
    radioPanel.add(privateRadio);
    gbc.gridx = 1;
    gbc.gridy = 4;
    formPanel.add(radioPanel, gbc);

    // Button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    buttonPanel.setBackground(Color.WHITE);

    JButton registerButton = new JButton("Register");
    registerButton.setFont(new Font("Arial", Font.BOLD, 16));
    registerButton.setPreferredSize(new Dimension(150, 40));
    registerButton.setBackground(new Color(60, 179, 113));
    registerButton.setForeground(Color.BLACK);
    registerButton.setFocusPainted(false);
    registerButton.addActionListener(e -> handleRegister());
    buttonPanel.add(registerButton);

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
    gbc.gridy = 5;
    gbc.gridwidth = 2;
    formPanel.add(buttonPanel, gbc);

    add(formPanel, BorderLayout.CENTER);
  }

  private void addFormField(JPanel panel, GridBagConstraints gbc, String label, int row) {
    JLabel fieldLabel = new JLabel(label);
    fieldLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridx = 0;
    gbc.gridy = row;
    gbc.weightx = 0.3;
    panel.add(fieldLabel, gbc);
  }

  private void handleRegister() {
    String email = emailField.getText().trim();
    String password = new String(passwordField.getPassword());
    String confirmPass = new String(confirmPasswordField.getPassword());
    String name = nameField.getText().trim();
    String isPublic = publicRadio.isSelected() ? "true" : "false";

    // Validation
    if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
      JOptionPane.showMessageDialog(
          this,
          "Please fill in all fields",
          "Validation Error",
          JOptionPane.WARNING_MESSAGE
      );
      return;
    }

    if (!email.contains("@")) {
      JOptionPane.showMessageDialog(
          this,
          "Please enter a valid email address",
          "Validation Error",
          JOptionPane.WARNING_MESSAGE
      );
      return;
    }

    if (!password.equals(confirmPass)) {
      JOptionPane.showMessageDialog(
          this,
          "Passwords do not match",
          "Validation Error",
          JOptionPane.WARNING_MESSAGE
      );
      confirmPasswordField.setText("");
      return;
    }

    if (password.length() < 6) {
      JOptionPane.showMessageDialog(
          this,
          "Password must be at least 6 characters",
          "Validation Error",
          JOptionPane.WARNING_MESSAGE
      );
      return;
    }

    // Create account and blogger
    Account account = new Account();
    account.setEmail(email);
    account.setPassword(password);

    Blogger blogger = new Blogger();
    blogger.setBloggerEmailAddress(email);
    blogger.setBloggerName(name);
    blogger.setisPublic(isPublic);

    Account registered = service.register(account, blogger);

    if (registered != null) {
      JOptionPane.showMessageDialog(
          this,
          "Registration successful! You can now login.",
          "Success",
          JOptionPane.INFORMATION_MESSAGE
      );
      clearFields();
      mainFrame.showPanel(MainFrame.LOGIN_PANEL);
    } else {
      JOptionPane.showMessageDialog(
          this,
          "Registration failed. Email may already exist.",
          "Registration Error",
          JOptionPane.ERROR_MESSAGE
      );
    }
  }

  private void clearFields() {
    emailField.setText("");
    passwordField.setText("");
    confirmPasswordField.setText("");
    nameField.setText("");
    publicRadio.setSelected(true);
  }
}