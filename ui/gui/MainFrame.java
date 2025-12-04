package com.ourblogs.ui.gui;

import com.ourblogs.entity.Account;
import com.ourblogs.service.Service;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
  private Service service;
  private Account loggedInAccount;
  private CardLayout cardLayout;
  private JPanel mainPanel;

  public static final String WELCOME_PANEL = "welcome";
  public static final String LOGIN_PANEL = "login";
  public static final String REGISTER_PANEL = "register";
  public static final String DASHBOARD_PANEL = "dashboard";

  public MainFrame(Service service) {
    this.service = service;
    initializeFrame();
    createPanels();
  }

  private void initializeFrame() {
    setTitle("OurBlogs - Social Blogging Platform");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(900, 700);
    setLocationRelativeTo(null);
    setResizable(true);

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);
    add(mainPanel);
  }

  private void createPanels() {
    WelcomePanel welcomePanel = new WelcomePanel(this);
    LoginPanel loginPanel = new LoginPanel(this, service);
    RegisterPanel registerPanel = new RegisterPanel(this, service);

    mainPanel.add(welcomePanel, WELCOME_PANEL);
    mainPanel.add(loginPanel, LOGIN_PANEL);
    mainPanel.add(registerPanel, REGISTER_PANEL);
    cardLayout.show(mainPanel, WELCOME_PANEL);
  }

  public void showPanel(String panelName) {
    cardLayout.show(mainPanel, panelName);
  }

  public void loginSuccess(Account account) {
    this.loggedInAccount = account;
    DashboardPanel dashboardPanel = new DashboardPanel(this, service, account);
    mainPanel.add(dashboardPanel, DASHBOARD_PANEL);
    showPanel(DASHBOARD_PANEL);
  }

  public void logout() {
    this.loggedInAccount = null;
    Component[] components = mainPanel.getComponents();
    for (Component comp : components) {
      if (comp instanceof DashboardPanel) {
        mainPanel.remove(comp);
        break;
      }
    }
    showPanel(WELCOME_PANEL);
  }

  public Account getLoggedInAccount() {
    return loggedInAccount;
  }
}