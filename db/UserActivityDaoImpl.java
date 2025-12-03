package com.ourblogs.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserActivityDaoImpl {
  private Connection connection;
  public UserActivityDaoImpl(Connection connection) { this.connection = connection; }

  public void logActivity(String email, String type, String details) {
    String sql = "INSERT INTO User_Activity (user_email, activity_type, related_entity) VALUES (?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, email);
      ps.setString(2, type);
      ps.setString(3, details);
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Error logging activity: " + e.getMessage());
    }
  }
}