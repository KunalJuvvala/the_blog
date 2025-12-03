package com.ourblogs.db;
import java.sql.*;
import java.util.ArrayList;
import com.ourblogs.entity.Tag;

public class TagDaoImpl {
  private Connection connection;
  public TagDaoImpl(Connection connection) { this.connection = connection; }

  public int getOrCreateTagId(String tagName) {
    String sqlFind = "SELECT tag_id FROM Tag WHERE tag_name = ?";
    try (PreparedStatement ps = connection.prepareStatement(sqlFind)) {
      ps.setString(1, tagName);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) return rs.getInt("tag_id");
    } catch (SQLException e) { e.printStackTrace(); }

    String sqlInsert = "INSERT INTO Tag (tag_name, tag_description) VALUES (?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, tagName);
      ps.setString(2, "User created tag");
      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next()) return rs.getInt(1);
    } catch (SQLException e) { e.printStackTrace(); }
    return -1;
  }

  public void addTagToBlog(int blogId, int tagId, String taggerEmail) {
    String sql = "INSERT INTO Blog_Tag (blog_id, tag_id, tagger_email) VALUES (?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setInt(1, blogId);
      ps.setInt(2, tagId);
      ps.setString(3, taggerEmail);
      ps.executeUpdate();
    } catch (SQLException e) {
      // Ignore duplicate tags on same blog
    }
  }
}