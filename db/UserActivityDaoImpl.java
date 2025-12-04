package com.ourblogs.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ourblogs.entity.UserActivity;

public class UserActivityDaoImpl {
  private Connection connection;

  public UserActivityDaoImpl(Connection connection) {
    this.connection = connection;
  }

  public void logActivity(String email, String type, String details) {
    String sql = "INSERT INTO User_Activity (user_email, activity_type, related_entity) VALUES (?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, email);
      ps.setString(2, type);
      ps.setString(3, details);
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Error logging activity: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public ArrayList<UserActivity> getActivitiesByUser(String email) {
    ArrayList<UserActivity> activities = new ArrayList<>();
    String sql = "SELECT * FROM User_Activity WHERE user_email = ? ORDER BY activity_date DESC";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          UserActivity activity = new UserActivity();
          activity.setActivityId(rs.getInt("activity_id"));
          activity.setUserEmail(rs.getString("user_email"));
          activity.setActivityType(rs.getString("activity_type"));
          activity.setActivityDate(rs.getTimestamp("activity_date"));
          activity.setRelatedEntity(rs.getString("related_entity"));
          activities.add(activity);
        }
      }
    } catch (SQLException e) {
      System.out.println("Error fetching activities: " + e.getMessage());
      e.printStackTrace();
    }
    return activities;
  }

  public ArrayList<UserActivity> getActivitiesByUserAndType(String email, String activityType) {
    ArrayList<UserActivity> activities = new ArrayList<>();
    String sql = "SELECT * FROM User_Activity WHERE user_email = ? AND activity_type = ? ORDER BY activity_date DESC";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, email);
      ps.setString(2, activityType);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          UserActivity activity = new UserActivity();
          activity.setActivityId(rs.getInt("activity_id"));
          activity.setUserEmail(rs.getString("user_email"));
          activity.setActivityType(rs.getString("activity_type"));
          activity.setActivityDate(rs.getTimestamp("activity_date"));
          activity.setRelatedEntity(rs.getString("related_entity"));
          activities.add(activity);
        }
      }
    } catch (SQLException e) {
      System.out.println("Error fetching activities by type: " + e.getMessage());
      e.printStackTrace();
    }
    return activities;
  }

  public ArrayList<UserActivity> getRecentActivities(int limit) {
    ArrayList<UserActivity> activities = new ArrayList<>();
    String sql = "SELECT * FROM User_Activity ORDER BY activity_date DESC LIMIT ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setInt(1, limit);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          UserActivity activity = new UserActivity();
          activity.setActivityId(rs.getInt("activity_id"));
          activity.setUserEmail(rs.getString("user_email"));
          activity.setActivityType(rs.getString("activity_type"));
          activity.setActivityDate(rs.getTimestamp("activity_date"));
          activity.setRelatedEntity(rs.getString("related_entity"));
          activities.add(activity);
        }
      }
    } catch (SQLException e) {
      System.out.println("Error fetching recent activities: " + e.getMessage());
      e.printStackTrace();
    }
    return activities;
  }
}