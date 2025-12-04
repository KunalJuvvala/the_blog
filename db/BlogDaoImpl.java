package com.ourblogs.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.ourblogs.entity.Blog;
import com.ourblogs.service.BlogDao;

public class BlogDaoImpl implements BlogDao {
  private Connection connection;

  public BlogDaoImpl(Connection connection) {
    this.connection = connection;
  }

  @Override
  public ArrayList<Blog> getBlogsByEmail(String email) {
    ArrayList<Blog> blogs = new ArrayList<>();
    String sql = "SELECT * FROM Blog WHERE blogger_email = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Blog acc = new Blog();
          int id = rs.getInt("id");
          Date blogDate = rs.getDate("date_blog");
          String body = rs.getString("body");
          String subject = rs.getString("subject");
          acc.setBlogId(id);
          acc.setBlogDate(blogDate);
          acc.setBlogBody(body);
          acc.setBlogSubject(subject);
          acc.setBloggerEmail(email);
          blogs.add(acc);
        }
      } catch (SQLException e) {
        System.out.println(e);
      }
    } catch (SQLException e) {
      System.out.println(e);
    }
    return blogs;
  }

  @Override
  public Blog insertBlog(Blog blog) {
    Blog fl = new Blog();
    String sql = "INSERT INTO blog (date_blog, body, subject, blogger_email) VALUES (?,?,?,?)";
    try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setDate(1, blog.getBlogDate());
      ps.setString(2, blog.getBlogBody());
      ps.setString(3, blog.getBlogSubject());
      ps.setString(4, blog.getBloggerEmail());
      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next()) {
        int generatedId = rs.getInt(1);
        blog.setBlogId(generatedId);
        fl = blog;

        // Log activity automatically
        logBlogCreation(blog.getBloggerEmail(), generatedId, blog.getBlogSubject());
      }
    } catch (SQLException e) {
      System.out.println(e);
    }
    return fl;
  }

  private void logBlogCreation(String email, int blogId, String subject) {
    String sql = "INSERT INTO User_Activity (user_email, activity_type, related_entity) VALUES (?, 'BLOG_CREATED', ?)";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setString(1, email);
      ps.setString(2, "Created blog: " + subject);
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Error logging blog creation: " + e.getMessage());
    }
  }

  @Override
  public boolean updateBlog(Blog blog) {
    String sql =
        "UPDATE blog SET date_blog = ?, body = ?, subject = ? WHERE id = ? AND blogger_email = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setDate(1, blog.getBlogDate());
      ps.setString(2, blog.getBlogBody());
      ps.setString(3, blog.getBlogSubject());
      ps.setInt(4, blog.getBlogId());
      ps.setString(5, blog.getBloggerEmail());

      int rowsAffected = ps.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.out.println(e);
      return false;
    }
  }

  @Override
  public boolean deleteBlog(int id) {
    String sql = "DELETE FROM blog WHERE id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setInt(1, id);

      int rowsAffected = ps.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.out.println(e);
      return false;
    }
  }
}

