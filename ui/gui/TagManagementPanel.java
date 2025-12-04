package com.ourblogs.ui.gui;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Tag;
import com.ourblogs.service.Service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TagManagementPanel extends JPanel {
  private Service service;
  private Account account;
  private JTextField blogIdField;
  private JTextField tagNameField;
  private JTable tagsTable;
  private DefaultTableModel tableModel;
  private JTextArea blogTagsArea;

  public TagManagementPanel(Service service, Account account) {
    this.service = service;
    this.account = account;
    initializeUI();
  }

  private void initializeUI() {
    setLayout(new BorderLayout(10, 10));
    setBackground(Color.WHITE);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel titleLabel = new JLabel("Tag Management");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    add(titleLabel, BorderLayout.NORTH);

    // Split pane
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setResizeWeight(0.6);

    // Left panel - Add tags to blog
    JPanel leftPanel = createAddTagPanel();
    splitPane.setLeftComponent(leftPanel);

    // Right panel - View all tags
    JPanel rightPanel = createViewTagsPanel();
    splitPane.setRightComponent(rightPanel);

    add(splitPane, BorderLayout.CENTER);
  }

  private JPanel createAddTagPanel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createTitledBorder("Add Tags to Blog"));

    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(Color.WHITE);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Blog ID
    JLabel idLabel = new JLabel("Blog ID:");
    idLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0.3;
    formPanel.add(idLabel, gbc);

    JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    idPanel.setBackground(Color.WHITE);
    blogIdField = new JTextField(10);
    blogIdField.setFont(new Font("Arial", Font.PLAIN, 14));
    idPanel.add(blogIdField);

    JButton loadButton = new JButton("Load Blog Tags");
    loadButton.setFont(new Font("Arial", Font.PLAIN, 12));
    loadButton.setForeground(Color.BLACK);
    loadButton.addActionListener(e -> loadBlogTags());
    idPanel.add(loadButton);

    gbc.gridx = 1;
    gbc.weightx = 0.7;
    formPanel.add(idPanel, gbc);

    // Tag name
    JLabel tagLabel = new JLabel("Tag Name:");
    tagLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.3;
    formPanel.add(tagLabel, gbc);

    tagNameField = new JTextField(20);
    tagNameField.setFont(new Font("Arial", Font.PLAIN, 14));
    gbc.gridx = 1;
    gbc.weightx = 0.7;
    formPanel.add(tagNameField, gbc);

    // Info label
    JLabel infoLabel = new JLabel("Tip: Separate multiple tags with commas");
    infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
    infoLabel.setForeground(Color.GRAY);
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    formPanel.add(infoLabel, gbc);

    panel.add(formPanel, BorderLayout.NORTH);

    // Display area for blog's tags
    JPanel displayPanel = new JPanel(new BorderLayout());
    displayPanel.setBackground(Color.WHITE);
    displayPanel.setBorder(BorderFactory.createTitledBorder("Current Tags for Blog"));

    blogTagsArea = new JTextArea(10, 30);
    blogTagsArea.setFont(new Font("Arial", Font.PLAIN, 14));
    blogTagsArea.setEditable(false);
    blogTagsArea.setLineWrap(true);
    blogTagsArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(blogTagsArea);
    displayPanel.add(scrollPane, BorderLayout.CENTER);

    panel.add(displayPanel, BorderLayout.CENTER);

    // Button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    buttonPanel.setBackground(Color.WHITE);

    JButton addTagButton = new JButton("Add Tag(s)");
    addTagButton.setFont(new Font("Arial", Font.BOLD, 14));
    addTagButton.setPreferredSize(new Dimension(150, 35));
    addTagButton.setBackground(new Color(70, 130, 180));
    addTagButton.setForeground(Color.BLACK);
    addTagButton.setFocusPainted(false);
    addTagButton.addActionListener(e -> addTags());
    buttonPanel.add(addTagButton);

    JButton clearButton = new JButton("Clear");
    clearButton.setFont(new Font("Arial", Font.PLAIN, 14));
    clearButton.setPreferredSize(new Dimension(150, 35));
    clearButton.setForeground(Color.BLACK);
    clearButton.addActionListener(e -> {
      blogIdField.setText("");
      tagNameField.setText("");
      blogTagsArea.setText("");
    });
    buttonPanel.add(clearButton);

    panel.add(buttonPanel, BorderLayout.SOUTH);

    return panel;
  }

  private JPanel createViewTagsPanel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createTitledBorder("All Tags in System"));

    // Table for all tags
    String[] columnNames = {"Tag ID", "Tag Name", "Description"};
    tableModel = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    tagsTable = new JTable(tableModel);
    tagsTable.setFont(new Font("Arial", Font.PLAIN, 14));
    tagsTable.setRowHeight(25);

    JScrollPane scrollPane = new JScrollPane(tagsTable);
    panel.add(scrollPane, BorderLayout.CENTER);

    JButton refreshButton = new JButton("Refresh Tags List");
    refreshButton.setFont(new Font("Arial", Font.PLAIN, 14));
    refreshButton.setForeground(Color.BLACK);
    refreshButton.addActionListener(e -> loadAllTags());
    panel.add(refreshButton, BorderLayout.SOUTH);

    loadAllTags();

    return panel;
  }

  private void addTags() {
    String idStr = blogIdField.getText().trim();
    String tagInput = tagNameField.getText().trim();

    if (idStr.isEmpty() || tagInput.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Please enter both Blog ID and tag name(s)",
          "Validation Error", JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      int blogId = Integer.parseInt(idStr);

      String[] tags = tagInput.split(",");
      int addedCount = 0;

      for (String tag : tags) {
        String trimmedTag = tag.trim();
        if (!trimmedTag.isEmpty()) {
          service.addTagToBlog(blogId, trimmedTag, account.getEmail());
          addedCount++;
        }
      }

      JOptionPane.showMessageDialog(this,
          addedCount + " tag(s) added successfully!", "Success",
          JOptionPane.INFORMATION_MESSAGE);

      tagNameField.setText("");
      loadBlogTags();
      loadAllTags();

    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid Blog ID", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void loadBlogTags() {
    String idStr = blogIdField.getText().trim();
    if (idStr.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Please enter a Blog ID", "Error",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      int blogId = Integer.parseInt(idStr);

      ArrayList<Tag> tags = service.getTagsForBlog(blogId);

      if (tags == null || tags.isEmpty()) {
        blogTagsArea.setText("Tags for Blog ID " + blogId + ":\n\nNo tags found for this blog.");
      } else {
        StringBuilder sb = new StringBuilder();
        sb.append("Tags for Blog ID ").append(blogId).append(":\n\n");

        for (Tag tag : tags) {
          sb.append("• ").append(tag.getTagName());
          if (tag.getTagDescription() != null && !tag.getTagDescription().isEmpty()) {
            sb.append(" - ").append(tag.getTagDescription());
          }
          sb.append("\n");
        }

        blogTagsArea.setText(sb.toString());
      }

    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid Blog ID", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void loadAllTags() {
    tableModel.setRowCount(0);

    ArrayList<Tag> tags = service.getAllTags();

    if (tags == null || tags.isEmpty()) {
      // Show message if no tags exist
      Object[] row = {"-", "No tags in system", "Add tags to blogs to see them here"};
      tableModel.addRow(row);
    } else {
      for (Tag tag : tags) {
        Object[] row = {
            tag.getTagId(),
            tag.getTagName(),
            tag.getTagDescription() != null ? tag.getTagDescription() : ""
        };
        tableModel.addRow(row);
      }
    }
  }
}