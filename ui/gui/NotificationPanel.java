package com.ourblogs.ui.gui;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Notifications;
import com.ourblogs.service.Service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class NotificationPanel extends JPanel {
  private Service service;
  private Account account;
  private JTable notificationTable;
  private DefaultTableModel tableModel;
  private JLabel unreadCountLabel;

  public NotificationPanel(Service service, Account account) {
    this.service = service;
    this.account = account;
    initializeUI();
    loadNotifications(true);
  }

  private void initializeUI() {
    setLayout(new BorderLayout(10, 10));
    setBackground(Color.WHITE);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // --- TOP PANEL ---
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(Color.WHITE);

    JLabel titleLabel = new JLabel("Notifications Center");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    topPanel.add(titleLabel, BorderLayout.WEST);

    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    rightPanel.setBackground(Color.WHITE);

    unreadCountLabel = new JLabel("Unread: 0");
    unreadCountLabel.setFont(new Font("Arial", Font.BOLD, 16));
    unreadCountLabel.setForeground(new Color(220, 20, 60));
    rightPanel.add(unreadCountLabel);

    JButton refreshButton = new JButton("Refresh");
    refreshButton.setFont(new Font("Arial", Font.PLAIN, 14));
    refreshButton.setForeground(Color.BLACK);
    // On manual refresh, we definitely want to see the popup if there are new items
    refreshButton.addActionListener(e -> loadNotifications(true));
    rightPanel.add(refreshButton);

    topPanel.add(rightPanel, BorderLayout.EAST);
    add(topPanel, BorderLayout.NORTH);

    // --- CENTER PANEL (Table) ---
    String[] columnNames = {"ID", "Type", "Message", "Date", "Status"};
    tableModel = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    notificationTable = new JTable(tableModel);
    notificationTable.setFont(new Font("Arial", Font.PLAIN, 14));
    notificationTable.setRowHeight(30);

    // Adjust column widths
    notificationTable.getColumnModel().getColumn(0).setPreferredWidth(50);
    notificationTable.getColumnModel().getColumn(1).setPreferredWidth(120);
    notificationTable.getColumnModel().getColumn(2).setPreferredWidth(400);
    notificationTable.getColumnModel().getColumn(3).setPreferredWidth(150);
    notificationTable.getColumnModel().getColumn(4).setPreferredWidth(80);

    JScrollPane scrollPane = new JScrollPane(notificationTable);
    add(scrollPane, BorderLayout.CENTER);

    // --- BOTTOM PANEL (Buttons) ---
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    buttonPanel.setBackground(Color.WHITE);

    JButton markReadButton = new JButton("Mark Selected as Read");
    markReadButton.setFont(new Font("Arial", Font.BOLD, 14));
    markReadButton.setPreferredSize(new Dimension(200, 35));
    markReadButton.setBackground(new Color(70, 130, 180));
    markReadButton.setForeground(Color.BLACK);
    markReadButton.setFocusPainted(false);
    markReadButton.addActionListener(e -> markSelectedAsRead());
    buttonPanel.add(markReadButton);

    JButton markAllReadButton = new JButton("Mark All as Read");
    markAllReadButton.setFont(new Font("Arial", Font.BOLD, 14));
    markAllReadButton.setPreferredSize(new Dimension(200, 35));
    markAllReadButton.setBackground(new Color(60, 179, 113));
    markAllReadButton.setForeground(Color.BLACK);
    markAllReadButton.setFocusPainted(false);
    markAllReadButton.addActionListener(e -> markAllAsRead());
    buttonPanel.add(markAllReadButton);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * Merged method to load data AND show popup
   * @param showPopup - if true, shows a dialog box for unread messages
   */
  private void loadNotifications(boolean showPopup) {
    tableModel.setRowCount(0);

    ArrayList<Notifications> notifications = service.getAllNotifications(account.getEmail());

    int unreadCount = 0;
    if (notifications != null) {
      for (Notifications notif : notifications) {
        Object[] row = {
            notif.getNotificationId(),
            notif.getNotificationType(),
            notif.getNotificationText(),
            notif.getNotificationDate(),
            notif.isRead() ? "Read" : "Unread"
        };
        tableModel.addRow(row);
        if (!notif.isRead()) {
          unreadCount++;
        }
      }
    }

    // Update the red text label
    unreadCountLabel.setText("Unread: " + unreadCount);

    // Trigger the Popup Alert if requested and count > 0
    if (showPopup && unreadCount > 0) {
      JOptionPane.showMessageDialog(this,
          "You have " + unreadCount + " new notifications!",
          "New Alerts",
          JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void markSelectedAsRead() {
    int selectedRow = notificationTable.getSelectedRow();
    if (selectedRow == -1) {
      JOptionPane.showMessageDialog(this, "Please select a notification", "Error",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    int notificationId = (int) tableModel.getValueAt(selectedRow, 0);
    String status = (String) tableModel.getValueAt(selectedRow, 4);

    if ("Read".equals(status)) {
      JOptionPane.showMessageDialog(this, "This notification is already marked as read",
          "Information", JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    service.markNotificationAsRead(notificationId);
    JOptionPane.showMessageDialog(this, "Notification marked as read", "Success",
        JOptionPane.INFORMATION_MESSAGE);

    // Reload without showing the popup again (to avoid annoyance)
    loadNotifications(false);
  }

  private void markAllAsRead() {
    int confirm = JOptionPane.showConfirmDialog(this,
        "Mark all notifications as read?", "Confirm",
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
      service.markAllNotificationsAsRead(account.getEmail());
      JOptionPane.showMessageDialog(this, "All notifications marked as read", "Success",
          JOptionPane.INFORMATION_MESSAGE);
      loadNotifications(false);
    }
  }
}