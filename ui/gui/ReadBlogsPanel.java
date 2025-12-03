package com.ourblogs.ui.gui;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blog;
import com.ourblogs.entity.Blogger;
import com.ourblogs.service.Service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

// Read Blogs Panel
public class ReadBlogsPanel extends JPanel {
  private Service service;
  private Account account;
  private JList<String> bloggerList;
  private DefaultListModel<String> bloggerListModel;
  private ArrayList<Blogger> bloggers;
  private JTextArea blogsArea;

  public ReadBlogsPanel(Service service, Account account) {
    this.service = service;
    this.account = account;
    initializeUI();
  }

  private void initializeUI() {
    setLayout(new BorderLayout(10, 10));
    setBackground(Color.WHITE);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel titleLabel = new JLabel("Read Blogs from Other Users");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    add(titleLabel, BorderLayout.NORTH);

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setResizeWeight(0.3);

    // Left: Blogger list
    JPanel listPanel = new JPanel(new BorderLayout());
    listPanel.setBackground(Color.WHITE);
    listPanel.setBorder(BorderFactory.createTitledBorder("Select Blogger"));

    bloggerListModel = new DefaultListModel<>();
    bloggerList = new JList<>(bloggerListModel);
    bloggerList.setFont(new Font("Arial", Font.PLAIN, 14));
    bloggerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    bloggerList.addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) {
        loadBlogsForSelectedBlogger();
      }
    });

    JScrollPane listScrollPane = new JScrollPane(bloggerList);
    listPanel.add(listScrollPane, BorderLayout.CENTER);

    JButton refreshButton = new JButton("Refresh List");
    refreshButton.setForeground(Color.BLACK);
    refreshButton.addActionListener(e -> loadBloggers());
    listPanel.add(refreshButton, BorderLayout.SOUTH);

    splitPane.setLeftComponent(listPanel);

    // Right: Blogs display
    JPanel blogsPanel = new JPanel(new BorderLayout());
    blogsPanel.setBackground(Color.WHITE);
    blogsPanel.setBorder(BorderFactory.createTitledBorder("Blogs"));

    blogsArea = new JTextArea();
    blogsArea.setFont(new Font("Arial", Font.PLAIN, 14));
    blogsArea.setEditable(false);
    blogsArea.setLineWrap(true);
    blogsArea.setWrapStyleWord(true);
    JScrollPane blogsScrollPane = new JScrollPane(blogsArea);
    blogsPanel.add(blogsScrollPane, BorderLayout.CENTER);

    splitPane.setRightComponent(blogsPanel);

    add(splitPane, BorderLayout.CENTER);

    loadBloggers();
  }

  private void loadBloggers() {
    bloggerListModel.clear();
    blogsArea.setText("");
    try {
      bloggers = service.getallBloggers();
      for (Blogger blogger : bloggers) {
        if (!blogger.getBloggerEmailAddress().equals(account.getEmail())) {
          String displayText =
              blogger.getBloggerName() + " (" + blogger.getBloggerEmailAddress() + ")";
          if ("true".equals(blogger.getisPublic())) {
            displayText += " [Public]";
          } else {
            displayText += " [Private]";
          }
          bloggerListModel.addElement(displayText);
        }
      }
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(this, "Error loading bloggers: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void loadBlogsForSelectedBlogger() {
    int selectedIndex = bloggerList.getSelectedIndex();
    if (selectedIndex == -1 || bloggers == null) {
      return;
    }

    // Adjust for skipped logged-in user
    int actualIndex = selectedIndex;
    for (int i = 0; i <= actualIndex; i++) {
      if (bloggers.get(i).getBloggerEmailAddress().equals(account.getEmail())) {
        actualIndex++;
      }
    }

    Blogger selectedBlogger = bloggers.get(actualIndex);
    boolean canRead = false;

    if ("true".equals(selectedBlogger.getisPublic())) {
      canRead = true;
    } else {
      try {
        int flag = service.isFriends(account.getEmail(), selectedBlogger.getBloggerEmailAddress());
        if (flag == 1) {
          canRead = true;
        }
      } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error checking permissions", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }
    }

    if (canRead) {
      ArrayList<Blog> blogs = service.viewBlogsbyEmail(selectedBlogger.getBloggerEmailAddress());
      if (blogs == null || blogs.isEmpty()) {
        blogsArea.setText("No blogs found for this user.");
      } else {
        StringBuilder sb = new StringBuilder();
        sb.append("Blogs by ").append(selectedBlogger.getBloggerName()).append(":\n");
        sb.append("=".repeat(60)).append("\n\n");
        for (Blog blog : blogs) {
          sb.append("ID: ").append(blog.getBlogId()).append("\n");
          sb.append("Subject: ").append(blog.getBlogSubject()).append("\n");
          sb.append("Date: ").append(blog.getBlogDate()).append("\n");
          sb.append("Body: ").append(blog.getBlogBody()).append("\n");
          sb.append("-".repeat(60)).append("\n\n");
        }
        blogsArea.setText(sb.toString());
        blogsArea.setCaretPosition(0);
      }
    } else {
      blogsArea.setText(
          "You do not have permission to read blogs from " + selectedBlogger.getBloggerName() +
              ".\n\nThis is a private account. You need to be added as a reader.");
    }
  }
}

// Add Readers Panel
class AddReadersPanel extends JPanel {
  private Service service;
  private Account account;
  private JList<String> bloggerList;
  private DefaultListModel<String> bloggerListModel;
  private ArrayList<Blogger> bloggers;
  private Blogger loggedInBlogger;

  public AddReadersPanel(Service service, Account account) {
    this.service = service;
    this.account = account;
    initializeUI();
  }

  private void initializeUI() {
    setLayout(new BorderLayout(10, 10));
    setBackground(Color.WHITE);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel titleLabel = new JLabel("Add Readers to Your Blog");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    add(titleLabel, BorderLayout.NORTH);

    try {
      loggedInBlogger = service.getBloggerByEmail(account.getEmail());

      if ("true".equals(loggedInBlogger.getisPublic())) {
        JPanel messagePanel = new JPanel(new GridBagLayout());
        messagePanel.setBackground(Color.WHITE);
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" +
            "Your account is PUBLIC.<br><br>" +
            "All users can read your blogs.<br><br>" +
            "No need to add individual readers." +
            "</div></html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        messagePanel.add(messageLabel);
        add(messagePanel, BorderLayout.CENTER);
        return;
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Error loading account information", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
    centerPanel.setBackground(Color.WHITE);

    JLabel infoLabel = new JLabel("Select bloggers to add as readers:");
    infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    centerPanel.add(infoLabel, BorderLayout.NORTH);

    bloggerListModel = new DefaultListModel<>();
    bloggerList = new JList<>(bloggerListModel);
    bloggerList.setFont(new Font("Arial", Font.PLAIN, 14));
    bloggerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scrollPane = new JScrollPane(bloggerList);
    centerPanel.add(scrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    buttonPanel.setBackground(Color.WHITE);

    JButton addButton = new JButton("Add Selected Reader");
    addButton.setFont(new Font("Arial", Font.BOLD, 16));
    addButton.setPreferredSize(new Dimension(200, 40));
    addButton.setBackground(new Color(70, 130, 180));
    addButton.setForeground(Color.BLACK);
    addButton.setFocusPainted(false);
    addButton.addActionListener(e -> handleAddReader());
    buttonPanel.add(addButton);

    JButton refreshButton = new JButton("Refresh List");
    refreshButton.setFont(new Font("Arial", Font.PLAIN, 16));
    refreshButton.setPreferredSize(new Dimension(150, 40));
    refreshButton.setForeground(Color.BLACK);
    refreshButton.addActionListener(e -> loadBloggers());
    buttonPanel.add(refreshButton);

    centerPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(centerPanel, BorderLayout.CENTER);

    loadBloggers();
  }

  private void loadBloggers() {
    bloggerListModel.clear();
    try {
      bloggers = service.getallBloggers();
      for (Blogger blogger : bloggers) {
        if (!blogger.getBloggerEmailAddress().equals(account.getEmail())) {
          int flag = service.isFriends(blogger.getBloggerEmailAddress(), account.getEmail());
          String displayText =
              blogger.getBloggerName() + " (" + blogger.getBloggerEmailAddress() + ")";
          if (flag == 1) {
            displayText += " [Already added]";
          }
          bloggerListModel.addElement(displayText);
        }
      }
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(this, "Error loading bloggers: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void handleAddReader() {
    int selectedIndex = bloggerList.getSelectedIndex();
    if (selectedIndex == -1 || bloggers == null) {
      JOptionPane.showMessageDialog(this, "Please select a blogger to add", "Error",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    int actualIndex = selectedIndex;
    for (int i = 0; i <= actualIndex; i++) {
      if (bloggers.get(i).getBloggerEmailAddress().equals(account.getEmail())) {
        actualIndex++;
      }
    }

    Blogger selectedBlogger = bloggers.get(actualIndex);

    try {
      int flag = service.isFriends(selectedBlogger.getBloggerEmailAddress(), account.getEmail());
      if (flag == 1) {
        JOptionPane.showMessageDialog(this,
            selectedBlogger.getBloggerName() + " is already a reader", "Information",
            JOptionPane.INFORMATION_MESSAGE);
        return;
      }

      service.addFriend(selectedBlogger, account.getEmail());
      JOptionPane.showMessageDialog(this,
          selectedBlogger.getBloggerName() + " added as reader successfully!", "Success",
          JOptionPane.INFORMATION_MESSAGE);
      loadBloggers();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Error adding reader: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }
}