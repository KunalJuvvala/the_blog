package com.ourblogs.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.ourblogs.entity.Tag;
import com.ourblogs.entity.BlogTag;

public class TagDaoImpl {
  private Connection connection;

  public TagDaoImpl(Connection connection) {
    this.connection = connection;
  }

  public int getOrCreateTagId(String tagName) {
    String sqlFind = "SELECT tag_id FROM Tag WHERE tag_name = ?";
    try (PreparedStatement ps = connection.prepareStatement(sqlFind)) {
      ps.setString(1, tagName);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getInt("tag_id");
        }
      }
    } catch (SQLException e) {
      System.out.println("Error finding tag: " + e.getMessage());
    }

    String sqlInsert = "INSERT INTO Tag (tag_name, tag_description) VALUES (?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, tagName);
      ps.setString(2, "User created tag");
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) {
          return rs.getInt(1);
        }
      }
    } catch (SQLException e) {
      System.out.println("Error creating tag: " + e.getMessage());
    }
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
      if (!e.getMessage().contains("Duplicate entry")) {
        System.out.println("Error adding tag to blog: " + e.getMessage());
      }
    }
  }

  public ArrayList<Tag> getTagsForBlog(int blogId) {
    ArrayList<Tag> tags = new ArrayList<>();
    String sql = "SELECT t.* FROM Tag t " +
        "JOIN Blog_Tag bt ON t.tag_id = bt.tag_id " +
        "WHERE bt.blog_id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setInt(1, blogId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Tag tag = new Tag();
          tag.setTagId(rs.getInt("tag_id"));
          tag.setTagName(rs.getString("tag_name"));
          tag.setCreatedDate(rs.getDate("created_date"));
          tag.setTagDescription(rs.getString("tag_description"));
          tags.add(tag);
        }
      }
    } catch (SQLException e) {
      System.out.println("Error fetching tags for blog: " + e.getMessage());
    }
    return tags;
  }

  public ArrayList<Tag> getAllTags() {
    ArrayList<Tag> tags = new ArrayList<>();
    String sql = "SELECT * FROM Tag ORDER BY tag_name";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Tag tag = new Tag();
          tag.setTagId(rs.getInt("tag_id"));
          tag.setTagName(rs.getString("tag_name"));
          tag.setCreatedDate(rs.getDate("created_date"));
          tag.setTagDescription(rs.getString("tag_description"));
          tags.add(tag);
        }
      }
    } catch (SQLException e) {
      System.out.println("Error fetching all tags: " + e.getMessage());
    }
    return tags;
  }

  public void removeTagFromBlog(int blogId, int tagId) {
    String sql = "DELETE FROM Blog_Tag WHERE blog_id = ? AND tag_id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setInt(1, blogId);
      ps.setInt(2, tagId);
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Error removing tag from blog: " + e.getMessage());
    }
  }

  public ArrayList<Integer> getBlogIdsByTag(String tagName) {
    ArrayList<Integer> blogIds = new ArrayList<>();
    String sql = "SELECT bt.blog_id FROM Blog_Tag bt " +
        "JOIN Tag t ON bt.tag_id = t.tag_id " +
        "WHERE t.tag_name = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, tagName);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          blogIds.add(rs.getInt("blog_id"));
        }
      }
    } catch (SQLException e) {
      System.out.println("Error fetching blogs by tag: " + e.getMessage());
    }
    return blogIds;
  }
}