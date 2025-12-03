package com.ourblogs.ui.gui;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blog;
import com.ourblogs.service.Service;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;

// Edit Blog Panel
public class EditBlogPanel extends JPanel {
  private Service service;
  private Account account;
  private JTextField blogIdField;
  private JTextField subjectField;
  private JTextArea bodyArea;

  public EditBlogPanel(Service service, Account account) {
    this.service = service;
    this.account = account;
    initializeUI();
  }

  private void initializeUI() {
    setLayout(new BorderLayout(10, 10));
    setBackground(Color.WHITE);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel titleLabel = new JLabel("Edit Blog Post");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    add(titleLabel, BorderLayout.NORTH);

    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(Color.WHITE);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Blog ID
    JLabel idLabel = new JLabel("Blog ID:");
    idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0.2;
    formPanel.add(idLabel, gbc);

    JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    idPanel.setBackground(Color.WHITE);
    blogIdField = new JTextField(10);
    blogIdField.setFont(new Font("Arial", Font.PLAIN, 14));
    idPanel.add(blogIdField);

    JButton loadButton = new JButton("Load Blog");
    loadButton.setFont(new Font("Arial", Font.PLAIN, 12));
    loadButton.setForeground(Color.BLACK);
    loadButton.addActionListener(e -> loadBlog());
    idPanel.add(loadButton);

    gbc.gridx = 1;
    gbc.weightx = 0.8;
    formPanel.add(idPanel, gbc);

    // Subject
    JLabel subjectLabel = new JLabel("New Subject:");
    subjectLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.2;
    formPanel.add(subjectLabel, gbc);

    subjectField = new JTextField();
    subjectField.setFont(new Font("Arial", Font.PLAIN, 14));
    gbc.gridx = 1;
    gbc.weightx = 0.8;
    formPanel.add(subjectField, gbc);

    // Body
    JLabel bodyLabel = new JLabel("New Body:");
    bodyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 0.2;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    formPanel.add(bodyLabel, gbc);

    bodyArea = new JTextArea(12, 40);
    bodyArea.setFont(new Font("Arial", Font.PLAIN, 14));
    bodyArea.setLineWrap(true);
    bodyArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(bodyArea);
    gbc.gridx = 1;
    gbc.weightx = 0.8;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty = 1.0;
    formPanel.add(scrollPane, gbc);

    add(formPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    buttonPanel.setBackground(Color.WHITE);

    JButton updateButton = new JButton("Update Blog");
    updateButton.setFont(new Font("Arial", Font.BOLD, 16));
    updateButton.setPreferredSize(new Dimension(150, 40));
    updateButton.setBackground(new Color(70, 130, 180));
    updateButton.setForeground(Color.BLACK);
    updateButton.setFocusPainted(false);
    updateButton.addActionListener(e -> handleUpdate());
    buttonPanel.add(updateButton);

    JButton clearButton = new JButton("Clear");
    clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
    clearButton.setPreferredSize(new Dimension(150, 40));
    clearButton.setForeground(Color.BLACK);
    clearButton.addActionListener(e -> clearFields());
    buttonPanel.add(clearButton);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  private void loadBlog() {
    String idStr = blogIdField.getText().trim();
    if (idStr.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Please enter a blog ID", "Error",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      int blogId = Integer.parseInt(idStr);
      ArrayList<Blog> blogs = service.viewBlogsbyEmail(account.getEmail());

      for (Blog blog : blogs) {
        if (blog.getBlogId() == blogId) {
          subjectField.setText(blog.getBlogSubject());
          bodyArea.setText(blog.getBlogBody());
          JOptionPane.showMessageDialog(this, "Blog loaded successfully", "Success",
              JOptionPane.INFORMATION_MESSAGE);
          return;
        }
      }
      JOptionPane.showMessageDialog(this, "Blog not found or doesn't belong to you", "Error",
          JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid Blog ID", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void handleUpdate() {
    String idStr = blogIdField.getText().trim();
    String subject = subjectField.getText().trim();
    String body = bodyArea.getText().trim();

    if (idStr.isEmpty() || subject.isEmpty() || body.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Please fill in all fields", "Validation Error",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      int blogId = Integer.parseInt(idStr);
      Blog blog = new Blog();
      blog.setBlogId(blogId);
      blog.setBlogSubject(subject);
      blog.setBlogBody(body);
      blog.setBloggerEmail(account.getEmail());
      blog.setBlogDate(new Date(System.currentTimeMillis()));

      boolean success = service.updateBlog(blog);
      if (success) {
        JOptionPane.showMessageDialog(this, "Blog updated successfully!", "Success",
            JOptionPane.INFORMATION_MESSAGE);
        clearFields();
      } else {
        JOptionPane.showMessageDialog(this, "Failed to update blog", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid Blog ID", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void clearFields() {
    blogIdField.setText("");
    subjectField.setText("");
    bodyArea.setText("");
  }
}

// Delete Blog Panel
class DeleteBlogPanel extends JPanel {
  private Service service;
  private Account account;
  private JTextField blogIdField;
  private JTextArea previewArea;

  public DeleteBlogPanel(Service service, Account account) {
    this.service = service;
    this.account = account;
    initializeUI();
  }

  private void initializeUI() {
    setLayout(new BorderLayout(10, 10));
    setBackground(Color.WHITE);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel titleLabel = new JLabel("Delete Blog Post");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    add(titleLabel, BorderLayout.NORTH);

    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(Color.WHITE);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel idLabel = new JLabel("Blog ID:");
    idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0.2;
    formPanel.add(idLabel, gbc);

    JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    idPanel.setBackground(Color.WHITE);
    blogIdField = new JTextField(10);
    blogIdField.setFont(new Font("Arial", Font.PLAIN, 14));
    idPanel.add(blogIdField);

    JButton previewButton = new JButton("Preview Blog");
    previewButton.setFont(new Font("Arial", Font.PLAIN, 12));
    previewButton.setForeground(Color.BLACK);
    previewButton.addActionListener(e -> previewBlog());
    idPanel.add(previewButton);

    gbc.gridx = 1;
    gbc.weightx = 0.8;
    formPanel.add(idPanel, gbc);

    JLabel previewLabel = new JLabel("Preview:");
    previewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.2;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    formPanel.add(previewLabel, gbc);

    previewArea = new JTextArea(12, 40);
    previewArea.setFont(new Font("Arial", Font.PLAIN, 14));
    previewArea.setEditable(false);
    previewArea.setLineWrap(true);
    previewArea.setWrapStyleWord(true);
    previewArea.setBackground(new Color(245, 245, 245));
    JScrollPane scrollPane = new JScrollPane(previewArea);
    gbc.gridx = 1;
    gbc.weightx = 0.8;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty = 1.0;
    formPanel.add(scrollPane, gbc);

    add(formPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    buttonPanel.setBackground(Color.WHITE);

    JButton deleteButton = new JButton("Delete Blog");
    deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
    deleteButton.setPreferredSize(new Dimension(150, 40));
    deleteButton.setBackground(new Color(220, 20, 60));
    deleteButton.setForeground(Color.BLACK);
    deleteButton.setFocusPainted(false);
    deleteButton.addActionListener(e -> handleDelete());
    buttonPanel.add(deleteButton);

    JButton clearButton = new JButton("Clear");
    clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
    clearButton.setPreferredSize(new Dimension(150, 40));
    clearButton.setForeground(Color.BLACK);
    clearButton.addActionListener(e -> clearFields());
    buttonPanel.add(clearButton);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  private void previewBlog() {
    String idStr = blogIdField.getText().trim();
    if (idStr.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Please enter a blog ID", "Error",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      int blogId = Integer.parseInt(idStr);
      ArrayList<Blog> blogs = service.viewBlogsbyEmail(account.getEmail());

      for (Blog blog : blogs) {
        if (blog.getBlogId() == blogId) {
          StringBuilder preview = new StringBuilder();
          preview.append("Subject: ").append(blog.getBlogSubject()).append("\n\n");
          preview.append("Date: ").append(blog.getBlogDate()).append("\n\n");
          preview.append("Body:\n").append(blog.getBlogBody());
          previewArea.setText(preview.toString());
          return;
        }
      }
      JOptionPane.showMessageDialog(this, "Blog not found or doesn't belong to you", "Error",
          JOptionPane.ERROR_MESSAGE);
      previewArea.setText("");
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid Blog ID", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void handleDelete() {
    String idStr = blogIdField.getText().trim();
    if (idStr.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Please enter a blog ID", "Validation Error",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to delete this blog? This action cannot be undone.",
        "Confirm Delete",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );

    if (confirm != JOptionPane.YES_OPTION) {
      return;
    }

    try {
      int blogId = Integer.parseInt(idStr);
      boolean success = service.deleteBlog(blogId);
      if (success) {
        JOptionPane.showMessageDialog(this, "Blog deleted successfully!", "Success",
            JOptionPane.INFORMATION_MESSAGE);
        clearFields();
      } else {
        JOptionPane.showMessageDialog(this, "Failed to delete blog", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid Blog ID", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void clearFields() {
    blogIdField.setText("");
    previewArea.setText("");
  }
}