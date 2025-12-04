package com.ourblogs.ui.gui;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blog;
import com.ourblogs.entity.Comment;
import com.ourblogs.entity.Rating;
import com.ourblogs.service.Service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class InteractionPanel extends JPanel {
  private Service service;
  private Account account;
  private JTextField blogIdField;
  private JTextArea commentTextArea;
  private JSpinner ratingSpinner;
  private JTextArea commentsDisplayArea;
  private JLabel avgRatingLabel;

  public InteractionPanel(Service service, Account account) {
    this.service = service;
    this.account = account;
    initializeUI();
  }

  private void initializeUI() {
    setLayout(new BorderLayout(10, 10));
    setBackground(Color.WHITE);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel titleLabel = new JLabel("Blog Interactions - Comments & Ratings");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    add(titleLabel, BorderLayout.NORTH);

    // Main split pane
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    splitPane.setResizeWeight(0.5);

    // Top panel - Add interactions
    JPanel topPanel = createInteractionPanel();
    splitPane.setTopComponent(topPanel);

    // Bottom panel - View interactions
    JPanel bottomPanel = createViewPanel();
    splitPane.setBottomComponent(bottomPanel);

    add(splitPane, BorderLayout.CENTER);
  }

  private JPanel createInteractionPanel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createTitledBorder("Add Comment or Rating"));

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
    loadButton.addActionListener(e -> loadBlogInteractions());
    idPanel.add(loadButton);

    gbc.gridx = 1;
    gbc.weightx = 0.8;
    formPanel.add(idPanel, gbc);

    // Comment section
    JLabel commentLabel = new JLabel("Comment:");
    commentLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.2;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    formPanel.add(commentLabel, gbc);

    commentTextArea = new JTextArea(4, 40);
    commentTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
    commentTextArea.setLineWrap(true);
    commentTextArea.setWrapStyleWord(true);
    JScrollPane commentScroll = new JScrollPane(commentTextArea);
    gbc.gridx = 1;
    gbc.weightx = 0.8;
    gbc.fill = GridBagConstraints.BOTH;
    formPanel.add(commentScroll, gbc);

    // Rating section
    JLabel ratingLabel = new JLabel("Rating (1-5):");
    ratingLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 0.2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    formPanel.add(ratingLabel, gbc);

    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(5, 1, 5, 1);
    ratingSpinner = new JSpinner(spinnerModel);
    ratingSpinner.setFont(new Font("Arial", Font.PLAIN, 14));
    gbc.gridx = 1;
    gbc.weightx = 0.8;
    formPanel.add(ratingSpinner, gbc);

    panel.add(formPanel, BorderLayout.CENTER);

    // Button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    buttonPanel.setBackground(Color.WHITE);

    JButton addCommentButton = new JButton("Add Comment");
    addCommentButton.setFont(new Font("Arial", Font.BOLD, 14));
    addCommentButton.setPreferredSize(new Dimension(150, 35));
    addCommentButton.setBackground(new Color(70, 130, 180));
    addCommentButton.setForeground(Color.BLACK);
    addCommentButton.setFocusPainted(false);
    addCommentButton.addActionListener(e -> addComment());
    buttonPanel.add(addCommentButton);

    JButton addRatingButton = new JButton("Add Rating");
    addRatingButton.setFont(new Font("Arial", Font.BOLD, 14));
    addRatingButton.setPreferredSize(new Dimension(150, 35));
    addRatingButton.setBackground(new Color(60, 179, 113));
    addRatingButton.setForeground(Color.BLACK);
    addRatingButton.setFocusPainted(false);
    addRatingButton.addActionListener(e -> addRating());
    buttonPanel.add(addRatingButton);

    panel.add(buttonPanel, BorderLayout.SOUTH);

    return panel;
  }

  private JPanel createViewPanel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createTitledBorder("View Comments & Ratings"));

    // Top info panel
    JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    infoPanel.setBackground(Color.WHITE);
    avgRatingLabel = new JLabel("Average Rating: N/A");
    avgRatingLabel.setFont(new Font("Arial", Font.BOLD, 16));
    infoPanel.add(avgRatingLabel);
    panel.add(infoPanel, BorderLayout.NORTH);

    // Comments display
    commentsDisplayArea = new JTextArea();
    commentsDisplayArea.setFont(new Font("Arial", Font.PLAIN, 14));
    commentsDisplayArea.setEditable(false);
    commentsDisplayArea.setLineWrap(true);
    commentsDisplayArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(commentsDisplayArea);
    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
  }

  private void loadBlogInteractions() {
    String idStr = blogIdField.getText().trim();
    if (idStr.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Please enter a blog ID", "Error",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      int blogId = Integer.parseInt(idStr);

      // Load average rating
      Double avgRating = service.getAverageRating(blogId);
      if (avgRating != null && avgRating > 0) {
        avgRatingLabel.setText(String.format("Average Rating: %.2f / 5.0", avgRating));
      } else {
        avgRatingLabel.setText("Average Rating: No ratings yet");
      }

      // Load comments
      ArrayList<Comment> comments = service.getCommentsByBlogId(blogId);
      if (comments.isEmpty()) {
        commentsDisplayArea.setText("No comments yet for this blog.");
      } else {
        StringBuilder sb = new StringBuilder();
        sb.append("=== COMMENTS ===\n\n");
        for (Comment comment : comments) {
          sb.append("From: ").append(comment.getCommenterEmail()).append("\n");
          sb.append("Date: ").append(comment.getCommentDate()).append("\n");
          sb.append("Comment: ").append(comment.getCommentText()).append("\n");
          sb.append("-".repeat(60)).append("\n\n");
        }
        commentsDisplayArea.setText(sb.toString());
        commentsDisplayArea.setCaretPosition(0);
      }

    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid Blog ID", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void addComment() {
    String idStr = blogIdField.getText().trim();
    String commentText = commentTextArea.getText().trim();

    if (idStr.isEmpty() || commentText.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Please enter both Blog ID and comment text",
          "Validation Error", JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      int blogId = Integer.parseInt(idStr);

      Comment comment = new Comment();
      comment.setBlogId(blogId);
      comment.setCommenterEmail(account.getEmail());
      comment.setCommentText(commentText);

      service.addComment(comment);

      JOptionPane.showMessageDialog(this, "Comment added successfully!", "Success",
          JOptionPane.INFORMATION_MESSAGE);

      commentTextArea.setText("");
      loadBlogInteractions();

    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid Blog ID", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void addRating() {
    String idStr = blogIdField.getText().trim();

    if (idStr.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Please enter a Blog ID", "Validation Error",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      int blogId = Integer.parseInt(idStr);
      int ratingValue = (Integer) ratingSpinner.getValue();

      // Check if user already rated this blog
      if (service.hasUserRatedBlog(blogId, account.getEmail())) {
        JOptionPane.showMessageDialog(this,
            "You have already rated this blog!", "Information",
            JOptionPane.INFORMATION_MESSAGE);
        return;
      }

      Rating rating = new Rating();
      rating.setBlogId(blogId);
      rating.setUserEmail(account.getEmail());
      rating.setRatingValue(ratingValue);

      service.addRating(rating);

      JOptionPane.showMessageDialog(this,
          "Rating of " + ratingValue + " stars added successfully!", "Success",
          JOptionPane.INFORMATION_MESSAGE);

      loadBlogInteractions();

    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Invalid Blog ID", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}