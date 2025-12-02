package com.ourblogs.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ourblogs.entity.Comment;
import com.ourblogs.entity.Rating;
import com.ourblogs.service.InteractionDao;

public class InteractionDaoImpl implements InteractionDao {
  private Connection connection;

  public InteractionDaoImpl(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void addComment(Comment comment) {
    String sql = "INSERT INTO comment (blog_id, commenter_email, comment_text) VALUES (?,?,?)";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setInt(1, comment.getBlogId());
      ps.setString(2, comment.getCommenterEmail());
      ps.setString(3, comment.getCommentText());
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public void addRating(Rating rating) {
    String sql = "INSERT INTO rating (blog_id, user_email, rating_value) VALUES (?,?,?)";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setInt(1, rating.getBlogId());
      ps.setString(2, rating.getUserEmail());
      ps.setInt(3, rating.getRatingValue());
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  @Override
  public ArrayList<Comment> getCommentsByBlogId(int blogId) {
    ArrayList<Comment> comments = new ArrayList<>();
    String sql = "SELECT * FROM comment WHERE blog_id = ? ORDER BY comment_date DESC";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setInt(1, blogId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Comment comment = new Comment();
          comment.setCommentId(rs.getInt("comment_id"));
          comment.setBlogId(rs.getInt("blog_id"));
          comment.setCommenterEmail(rs.getString("commenter_email"));
          comment.setCommentText(rs.getString("comment_text"));
          comment.setCommentDate(rs.getTimestamp("comment_date"));
          comments.add(comment);
        }
      }
    } catch (SQLException e) {
      System.out.println(e);
    }
    return comments;
  }

  @Override
  public Double getAverageRating(int blogId) {
    String sql = "SELECT AVG(rating_value) as avg_rating FROM rating WHERE blog_id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setInt(1, blogId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getDouble("avg_rating");
        }
      }
    } catch (SQLException e) {
      System.out.println(e);
    }
    return null;
  }

  @Override
  public boolean hasUserRatedBlog(int blogId, String userEmail) {
    String sql = "SELECT * FROM rating WHERE blog_id = ? AND user_email = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setInt(1, blogId);
      ps.setString(2, userEmail);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException e) {
      System.out.println(e);
    }
    return false;
  }
}