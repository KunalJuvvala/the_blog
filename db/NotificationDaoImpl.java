package com.ourblogs.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ourblogs.entity.Notifications;

public class NotificationDaoImpl {
  private Connection connection;
  public NotificationDaoImpl(Connection connection) { this.connection = connection; }

  public void createNotification(String recipient, String type, String text) {
    String sql = "INSERT INTO Notification (recipient_email, notification_type, notification_text) VALUES (?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, recipient);
      ps.setString(2, type);
      ps.setString(3, text);
      ps.executeUpdate();
    } catch (SQLException e) { e.printStackTrace(); }
  }

  public ArrayList<Notifications> getUnreadNotifications(String email) {
    ArrayList<Notifications> list = new ArrayList<>();
    String sql = "SELECT * FROM Notification WHERE recipient_email = ? AND is_read = false";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, email);
      ResultSet rs = ps.executeQuery();
      while(rs.next()){
        Notifications n = new Notifications();
        n.setNotificationId(rs.getInt("notification_id"));
        n.setRecipientEmail(email);
        n.setNotificationText(rs.getString("notification_text"));
        n.setNotificationDate(rs.getTimestamp("notification_date"));
        list.add(n);
      }
    } catch (SQLException e) { e.printStackTrace(); }
    return list;
  }
}