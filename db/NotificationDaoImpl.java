package com.ourblogs.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ourblogs.entity.Notifications;

public class NotificationDaoImpl {
  private Connection connection;

  public NotificationDaoImpl(Connection connection) {
    this.connection = connection;
  }

  public void createNotification(String recipient, String type, String text) {
    String sql = "INSERT INTO Notification (recipient_email, notification_type, notification_text) VALUES (?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, recipient);
      ps.setString(2, type);
      ps.setString(3, text);
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Error creating notification: " + e.getMessage());
    }
  }

  public ArrayList<Notifications> getUnreadNotifications(String email) {
    ArrayList<Notifications> list = new ArrayList<>();
    String sql = "SELECT * FROM Notification WHERE recipient_email = ? AND is_read = false ORDER BY notification_date DESC";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Notifications n = new Notifications();
          n.setNotificationId(rs.getInt("notification_id"));
          n.setRecipientEmail(rs.getString("recipient_email"));
          n.setNotificationType(rs.getString("notification_type"));
          n.setNotificationText(rs.getString("notification_text"));
          n.setNotificationDate(rs.getTimestamp("notification_date"));
          n.setRead(rs.getBoolean("is_read"));
          list.add(n);
        }
      }
    } catch (SQLException e) {
      System.out.println("Error fetching notifications: " + e.getMessage());
    }
    return list;
  }

  public ArrayList<Notifications> getAllNotifications(String email) {
    ArrayList<Notifications> list = new ArrayList<>();
    String sql = "SELECT * FROM Notification WHERE recipient_email = ? ORDER BY notification_date DESC";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Notifications n = new Notifications();
          n.setNotificationId(rs.getInt("notification_id"));
          n.setRecipientEmail(rs.getString("recipient_email"));
          n.setNotificationType(rs.getString("notification_type"));
          n.setNotificationText(rs.getString("notification_text"));
          n.setNotificationDate(rs.getTimestamp("notification_date"));
          n.setRead(rs.getBoolean("is_read"));
          list.add(n);
        }
      }
    } catch (SQLException e) {
      System.out.println("Error fetching all notifications: " + e.getMessage());
    }
    return list;
  }

  // THIS METHOD WAS MISSING - ADD IT
  public void markAsRead(int notificationId) {
    String sql = "UPDATE Notification SET is_read = true WHERE notification_id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setInt(1, notificationId);
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Error marking notification as read: " + e.getMessage());
    }
  }

  // THIS METHOD WAS MISSING - ADD IT
  public void markAllAsRead(String email) {
    String sql = "UPDATE Notification SET is_read = true WHERE recipient_email = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, email);
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Error marking all notifications as read: " + e.getMessage());
    }
  }

  // THIS METHOD WAS MISSING - ADD IT
  public int getUnreadCount(String email) {
    String sql = "SELECT COUNT(*) as count FROM Notification WHERE recipient_email = ? AND is_read = false";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getInt("count");
        }
      }
    } catch (SQLException e) {
      System.out.println("Error getting unread count: " + e.getMessage());
    }
    return 0;
  }
}