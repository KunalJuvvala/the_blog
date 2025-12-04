package com.ourblogs.ui.gui;

import com.ourblogs.entity.Account;
import com.ourblogs.service.Service;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
  private MainFrame mainFrame;
  private Service service;
  private Account account;
  private CardLayout contentLayout;
  private JPanel contentPanel;

  // Panel names
  private static final String CREATE_BLOG = "createBlog";
  private static final String VIEW_BLOGS = "viewBlogs";
  private static final String READ_BLOGS = "readBlogs";
  private static final String ADD_READERS = "addReaders";
  private static final String EDIT_BLOG = "editBlog";
  private static final String DELETE_BLOG = "deleteBlog";
  private static final String INTERACTIONS = "interactions";
  private static final String NOTIFICATIONS = "notifications";
  private static final String TAG_MANAGEMENT = "tagManagement";
  private static final String USER_ACTIVITY = "userActivity";

  public DashboardPanel(MainFrame mainFrame, Service service, Account account) {
    this.mainFrame = mainFrame;
    this.service = service;
    this.account = account;
    initializeUI();
  }

  private void initializeUI() {
    setLayout(new BorderLayout());

    // Top navigation panel
    JPanel navPanel = createNavigationPanel();
    add(navPanel, BorderLayout.NORTH);

    // Sidebar with menu buttons
    JPanel sidebarPanel = createSidebarPanel();
    add(sidebarPanel, BorderLayout.WEST);

    // Content area with CardLayout
    contentLayout = new CardLayout();
    contentPanel = new JPanel(contentLayout);
    contentPanel.setBackground(Color.WHITE);

    // Add all content panels
    contentPanel.add(new WelcomeDashboardPanel(account), "welcome");
    contentPanel.add(new CreateBlogPanel(service, account), CREATE_BLOG);
    contentPanel.add(new ViewBlogsPanel(service, account), VIEW_BLOGS);
    contentPanel.add(new ReadBlogsPanel(service, account), READ_BLOGS);
    contentPanel.add(new AddReadersPanel(service, account), ADD_READERS);
    contentPanel.add(new EditBlogPanel(service, account), EDIT_BLOG);
    contentPanel.add(new DeleteBlogPanel(service, account), DELETE_BLOG);
    contentPanel.add(new InteractionPanel(service, account), INTERACTIONS);
    contentPanel.add(new NotificationPanel(service, account), NOTIFICATIONS);
    contentPanel.add(new TagManagementPanel(service, account), TAG_MANAGEMENT);
    contentPanel.add(new UserActivityPanel(service, account), USER_ACTIVITY);

    add(contentPanel, BorderLayout.CENTER);

    // Show welcome panel initially
    contentLayout.show(contentPanel, "welcome");
  }

  private JPanel createNavigationPanel() {
    JPanel navPanel = new JPanel(new BorderLayout());
    navPanel.setBackground(new Color(70, 130, 180));
    navPanel.setPreferredSize(new Dimension(0, 60));

    JLabel titleLabel = new JLabel("  OurBlogs Dashboard");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setForeground(Color.WHITE);
    navPanel.add(titleLabel, BorderLayout.WEST);

    JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    userPanel.setBackground(new Color(70, 130, 180));
    JLabel userLabel = new JLabel("Logged in as: " + account.getEmail() + "  ");
    userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    userLabel.setForeground(Color.WHITE);
    userPanel.add(userLabel);

    JButton logoutButton = new JButton("Logout");
    logoutButton.setFont(new Font("Arial", Font.BOLD, 12));
    logoutButton.setBackground(new Color(220, 20, 60));
    logoutButton.setForeground(Color.BLACK);
    logoutButton.setFocusPainted(false);
    logoutButton.addActionListener(e -> mainFrame.logout());
    userPanel.add(logoutButton);

    navPanel.add(userPanel, BorderLayout.EAST);

    return navPanel;
  }

  private JPanel createSidebarPanel() {
    JPanel sidebarPanel = new JPanel();
    sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
    sidebarPanel.setBackground(new Color(245, 245, 245));
    sidebarPanel.setPreferredSize(new Dimension(200, 0));
    sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

    // Add padding at top
    sidebarPanel.add(Box.createVerticalStrut(20));

    // Menu buttons
    addMenuButton(sidebarPanel, "Create Blog", CREATE_BLOG);
    addMenuButton(sidebarPanel, "View My Blogs", VIEW_BLOGS);
    addMenuButton(sidebarPanel, "Read Blogs", READ_BLOGS);
    addMenuButton(sidebarPanel, "Add Readers", ADD_READERS);
    addMenuButton(sidebarPanel, "Edit Blog", EDIT_BLOG);
    addMenuButton(sidebarPanel, "Delete Blog", DELETE_BLOG);
    addMenuButton(sidebarPanel, "Comments & Ratings", INTERACTIONS);
    addMenuButton(sidebarPanel, "Notifications", NOTIFICATIONS);
    addMenuButton(sidebarPanel, "Tag Management", TAG_MANAGEMENT);
    addMenuButton(sidebarPanel, "My Activity", USER_ACTIVITY);

    sidebarPanel.add(Box.createVerticalGlue());

    return sidebarPanel;
  }

  private void addMenuButton(JPanel panel, String text, String panelName) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.PLAIN, 14));
    button.setMaximumSize(new Dimension(180, 40));
    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    button.setFocusPainted(false);
    button.setBackground(Color.WHITE);
    button.setForeground(Color.BLACK);
    button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    button.addActionListener(e -> contentLayout.show(contentPanel, panelName));
    panel.add(button);
    panel.add(Box.createVerticalStrut(10));
  }
}

// Welcome Dashboard Panel
class WelcomeDashboardPanel extends JPanel {
  public WelcomeDashboardPanel(Account account) {
    setLayout(new BorderLayout());
    setBackground(Color.WHITE);

    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(Color.WHITE);

    JLabel welcomeLabel = new JLabel("Welcome to Your Dashboard, " + account.getEmail() + "!");
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(20, 20, 20, 20);
    centerPanel.add(welcomeLabel, gbc);

    add(centerPanel, BorderLayout.CENTER);
  }
}