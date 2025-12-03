package com.ourblogs.ui.gui;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blog;
import com.ourblogs.service.Service;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class CreateBlogPanel extends JPanel {
  private Service service;
  private Account account;
  private JTextField subjectField;
  private JTextArea bodyArea;

  public CreateBlogPanel(Service service, Account account) {
    this.service = service;
    this.account = account;
    initializeUI();
  }

  private void initializeUI() {
    setLayout(new BorderLayout(10, 10));
    setBackground(Color.WHITE);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Title
    JLabel titleLabel = new JLabel("Create New Blog Post");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    add(titleLabel, BorderLayout.NORTH);

    // Form panel
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(Color.WHITE);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Subject
    JLabel subjectLabel = new JLabel("Subject:");
    subjectLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0.2;
    formPanel.add(subjectLabel, gbc);

    subjectField = new JTextField();
    subjectField.setFont(new Font("Arial", Font.PLAIN, 14));
    subjectField.setPreferredSize(new Dimension(0, 35));
    gbc.gridx = 1;
    gbc.weightx = 0.8;
    formPanel.add(subjectField, gbc);

    // Body
    JLabel bodyLabel = new JLabel("Body:");
    bodyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.2;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    formPanel.add(bodyLabel, gbc);

    bodyArea = new JTextArea(15, 40);
    bodyArea.setFont(new Font("Arial", Font.PLAIN, 14));
    bodyArea.setLineWrap(true);
    bodyArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(bodyArea);
    scrollPane.setPreferredSize(new Dimension(500, 300));
    gbc.gridx = 1;
    gbc.weightx = 0.8;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty = 1.0;
    formPanel.add(scrollPane, gbc);

    add(formPanel, BorderLayout.CENTER);

    // Button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    buttonPanel.setBackground(Color.WHITE);

    JButton createButton = new JButton("Create Blog");
    createButton.setFont(new Font("Arial", Font.BOLD, 16));
    createButton.setPreferredSize(new Dimension(150, 40));
    createButton.setBackground(new Color(70, 130, 180));
    createButton.setForeground(Color.BLACK);
    createButton.setFocusPainted(false);
    createButton.addActionListener(e -> handleCreateBlog());
    buttonPanel.add(createButton);

    JButton clearButton = new JButton("Clear");
    clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
    clearButton.setPreferredSize(new Dimension(150, 40));
    clearButton.setForeground(Color.BLACK);
    clearButton.addActionListener(e -> clearFields());
    buttonPanel.add(clearButton);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  private void handleCreateBlog() {
    String subject = subjectField.getText().trim();
    String body = bodyArea.getText().trim();

    if (subject.isEmpty() || body.isEmpty()) {
      JOptionPane.showMessageDialog(
          this,
          "Please fill in both subject and body",
          "Validation Error",
          JOptionPane.WARNING_MESSAGE
      );
      return;
    }

    Blog blog = new Blog();
    blog.setBlogSubject(subject);
    blog.setBlogBody(body);
    blog.setBloggerEmail(account.getEmail());
    blog.setBlogDate(new Date(System.currentTimeMillis()));

    Blog createdBlog = service.insertBlog(blog);

    if (createdBlog != null) {
      JOptionPane.showMessageDialog(
          this,
          "Blog created successfully!\n" +
              "ID: " + createdBlog.getBlogId() + "\n" +
              "Subject: " + createdBlog.getBlogSubject(),
          "Success",
          JOptionPane.INFORMATION_MESSAGE
      );
      clearFields();
    } else {
      JOptionPane.showMessageDialog(
          this,
          "Failed to create blog",
          "Error",
          JOptionPane.ERROR_MESSAGE
      );
    }
  }

  private void clearFields() {
    subjectField.setText("");
    bodyArea.setText("");
  }
}