package com.ourblogs.ui.gui;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
  private MainFrame mainFrame;

  public WelcomePanel(MainFrame mainFrame) {
    this.mainFrame = mainFrame;
    initializeUI();
  }

  private void initializeUI() {
    setLayout(new BorderLayout());
    setBackground(new Color(240, 248, 255));

    // Header Panel
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(70, 130, 180));
    headerPanel.setPreferredSize(new Dimension(0, 100));
    JLabel titleLabel = new JLabel("OurBlogs");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
    titleLabel.setForeground(Color.WHITE);
    headerPanel.add(titleLabel);
    add(headerPanel, BorderLayout.NORTH);

    // Center Panel
    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(new Color(240, 248, 255));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    // Welcome message
    JLabel welcomeLabel = new JLabel("Welcome to the Social Blogging Platform");
    welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 24));
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    centerPanel.add(welcomeLabel, gbc);

    // Description
    JLabel descLabel = new JLabel(
        "<html><center>Share your thoughts, connect with bloggers,<br>and engage with the community</center></html>");
    descLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    descLabel.setForeground(Color.GRAY);
    gbc.gridy = 1;
    centerPanel.add(descLabel, gbc);

    // Login Button
    JButton loginButton = new JButton("Login");
    loginButton.setFont(new Font("Arial", Font.BOLD, 18));
    loginButton.setPreferredSize(new Dimension(200, 50));
    loginButton.setBackground(new Color(70, 130, 180));
    loginButton.setForeground(Color.BLACK);
    loginButton.setFocusPainted(false);
    loginButton.addActionListener(e -> mainFrame.showPanel(MainFrame.LOGIN_PANEL));
    gbc.gridy = 2;
    gbc.gridwidth = 1;
    centerPanel.add(loginButton, gbc);

    // Register Button
    JButton registerButton = new JButton("Register");
    registerButton.setFont(new Font("Arial", Font.BOLD, 18));
    registerButton.setPreferredSize(new Dimension(200, 50));
    registerButton.setBackground(new Color(60, 179, 113));
    registerButton.setForeground(Color.BLACK);
    registerButton.setFocusPainted(false);
    registerButton.addActionListener(e -> mainFrame.showPanel(MainFrame.REGISTER_PANEL));
    gbc.gridx = 1;
    centerPanel.add(registerButton, gbc);

    // Exit Button
    JButton exitButton = new JButton("Exit");
    exitButton.setFont(new Font("Arial", Font.PLAIN, 14));
    exitButton.setPreferredSize(new Dimension(200, 40));
    exitButton.setForeground(Color.BLACK);
    exitButton.addActionListener(e -> System.exit(0));
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    centerPanel.add(exitButton, gbc);

    add(centerPanel, BorderLayout.CENTER);

    // Footer
    JPanel footerPanel = new JPanel();
    footerPanel.setBackground(new Color(240, 248, 255));
    JLabel footerLabel = new JLabel("© 2025 OurBlogs Platform - DBMS Project");
    footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
    footerLabel.setForeground(Color.GRAY);
    footerPanel.add(footerLabel);
    add(footerPanel, BorderLayout.SOUTH);
  }
}