package com.ourblogs.ui.gui;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.UserActivity;
import com.ourblogs.service.Service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class UserActivityPanel extends JPanel {
  private Service service;
  private Account account;
  private JTable activityTable;
  private DefaultTableModel tableModel;
  private JComboBox<String> filterComboBox;

  public UserActivityPanel(Service service, Account account) {
    this.service = service;
    this.account = account;
    initializeUI();
    loadActivities();
  }

  private void initializeUI() {
    setLayout(new BorderLayout(10, 10));
    setBackground(Color.WHITE);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Top panel
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(Color.WHITE);

    JLabel titleLabel = new JLabel("My Activity History");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    topPanel.add(titleLabel, BorderLayout.WEST);

    JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    filterPanel.setBackground(Color.WHITE);

    JLabel filterLabel = new JLabel("Filter by:");
    filterLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    filterPanel.add(filterLabel);

    String[] filterOptions = {"All Activities", "BLOG_CREATED", "COMMENT", "RATING", "USER_LOGIN"};
    filterComboBox = new JComboBox<>(filterOptions);
    filterComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
    filterComboBox.addActionListener(e -> loadActivities());
    filterPanel.add(filterComboBox);

    JButton refreshButton = new JButton("Refresh");
    refreshButton.setFont(new Font("Arial", Font.PLAIN, 14));
    refreshButton.setForeground(Color.BLACK);
    refreshButton.addActionListener(e -> loadActivities());
    filterPanel.add(refreshButton);

    topPanel.add(filterPanel, BorderLayout.EAST);
    add(topPanel, BorderLayout.NORTH);

    // Table
    String[] columnNames = {"Activity ID", "Type", "Description", "Date"};
    tableModel = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    activityTable = new JTable(tableModel);
    activityTable.setFont(new Font("Arial", Font.PLAIN, 14));
    activityTable.setRowHeight(30);
    activityTable.getColumnModel().getColumn(0).setPreferredWidth(100);
    activityTable.getColumnModel().getColumn(1).setPreferredWidth(150);
    activityTable.getColumnModel().getColumn(2).setPreferredWidth(350);
    activityTable.getColumnModel().getColumn(3).setPreferredWidth(200);

    JScrollPane scrollPane = new JScrollPane(activityTable);
    add(scrollPane, BorderLayout.CENTER);

    // Info panel
    JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    infoPanel.setBackground(Color.WHITE);
    JLabel infoLabel = new JLabel(
        "This shows your activity history on the platform including blogs, comments, and interactions");
    infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
    infoLabel.setForeground(Color.GRAY);
    infoPanel.add(infoLabel);
    add(infoPanel, BorderLayout.SOUTH);
  }

  private void loadActivities() {
    tableModel.setRowCount(0);

    String selectedFilter = (String) filterComboBox.getSelectedItem();
    ArrayList<UserActivity> activities;

    if ("All Activities".equals(selectedFilter)) {
      activities = service.getUserActivities(account.getEmail());
    } else {
      activities = service.getUserActivitiesByType(account.getEmail(), selectedFilter);
    }

    if (activities == null || activities.isEmpty()) {
      // Show message if no activities
      Object[] row = {"-", "No activities", "No activity records found for this user", "-"};
      tableModel.addRow(row);
    } else {
      for (UserActivity activity : activities) {
        Object[] row = {
            activity.getActivityId(),
            activity.getActivityType(),
            activity.getRelatedEntity() != null ? activity.getRelatedEntity() : "N/A",
            activity.getActivityDate()
        };
        tableModel.addRow(row);
      }
    }
  }
}