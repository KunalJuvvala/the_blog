package com.ourblogs.ui.gui;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blog;
import com.ourblogs.service.Service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ViewBlogsPanel extends JPanel {
  private Service service;
  private Account account;
  private JTable blogsTable;
  private DefaultTableModel tableModel;
  private JTextArea detailsArea;

  public ViewBlogsPanel(Service service, Account account) {
    this.service = service;
    this.account = account;
    initializeUI();
  }

  private void initializeUI() {
    setLayout(new BorderLayout(10, 10));
    setBackground(Color.WHITE);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Title and refresh button panel
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(Color.WHITE);

    JLabel titleLabel = new JLabel("My Blog Posts");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    topPanel.add(titleLabel, BorderLayout.WEST);

    JButton refreshButton = new JButton("Refresh");
    refreshButton.setFont(new Font("Arial", Font.PLAIN, 14));
    refreshButton.setForeground(Color.BLACK);
    refreshButton.addActionListener(e -> loadBlogs());
    topPanel.add(refreshButton, BorderLayout.EAST);

    add(topPanel, BorderLayout.NORTH);

    // Split pane with table and details
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    splitPane.setResizeWeight(0.6);

    // Table panel
    JPanel tablePanel = new JPanel(new BorderLayout());
    tablePanel.setBackground(Color.WHITE);

    String[] columnNames = {"ID", "Subject", "Date"};
    tableModel = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    blogsTable = new JTable(tableModel);
    blogsTable.setFont(new Font("Arial", Font.PLAIN, 14));
    blogsTable.setRowHeight(25);
    blogsTable.getSelectionModel().addListSelectionListener(e -> {
      if (!e.getValueIsAdjusting()) {
        showBlogDetails();
      }
    });

    JScrollPane tableScrollPane = new JScrollPane(blogsTable);
    tablePanel.add(tableScrollPane, BorderLayout.CENTER);

    splitPane.setTopComponent(tablePanel);

    // Details panel
    JPanel detailsPanel = new JPanel(new BorderLayout());
    detailsPanel.setBackground(Color.WHITE);
    detailsPanel.setBorder(BorderFactory.createTitledBorder("Blog Details"));

    detailsArea = new JTextArea();
    detailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
    detailsArea.setEditable(false);
    detailsArea.setLineWrap(true);
    detailsArea.setWrapStyleWord(true);
    JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
    detailsPanel.add(detailsScrollPane, BorderLayout.CENTER);

    splitPane.setBottomComponent(detailsPanel);

    add(splitPane, BorderLayout.CENTER);

    // Load blogs
    loadBlogs();
  }

  private void loadBlogs() {
    tableModel.setRowCount(0);
    detailsArea.setText("");

    ArrayList<Blog> blogs = service.viewBlogsbyEmail(account.getEmail());

    if (blogs == null || blogs.isEmpty()) {
      JOptionPane.showMessageDialog(
          this,
          "No blogs found",
          "Information",
          JOptionPane.INFORMATION_MESSAGE
      );
      return;
    }

    for (Blog blog : blogs) {
      Object[] row = {
          blog.getBlogId(),
          blog.getBlogSubject(),
          blog.getBlogDate()
      };
      tableModel.addRow(row);
    }
  }

  private void showBlogDetails() {
    int selectedRow = blogsTable.getSelectedRow();
    if (selectedRow == -1) {
      return;
    }

    int blogId = (int) tableModel.getValueAt(selectedRow, 0);
    ArrayList<Blog> blogs = service.viewBlogsbyEmail(account.getEmail());

    for (Blog blog : blogs) {
      if (blog.getBlogId() == blogId) {
        StringBuilder details = new StringBuilder();
        details.append("Subject: ").append(blog.getBlogSubject()).append("\n\n");
        details.append("Date: ").append(blog.getBlogDate()).append("\n\n");
        details.append("Body:\n").append(blog.getBlogBody());
        detailsArea.setText(details.toString());
        break;
      }
    }
  }
}