package com.ourblogs.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ourblogs.entity.Blog;
import com.ourblogs.service.BlogDao;

public class BlogDaoImpl implements BlogDao {
   private Connection connection;
	
   public BlogDaoImpl(Connection connection) {
      this.connection=connection;
   }
@Override
   public ArrayList<Blog> getBlogsByEmail(String email) {
      ArrayList<Blog> blogs = new ArrayList<>();
      String sql = "SELECT * FROM Blog WHERE blogger_email = ?";
      try (PreparedStatement ps = connection.prepareStatement(sql)) {
         ps.setString(1, email);
         try (ResultSet rs = ps.executeQuery()){
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
         } catch (SQLException e){
            System.out.println(e);
         }
      } catch (SQLException e) {
         System.out.println(e);
      } 
      return blogs;
   }
@Override
	
   public Blog insertBlog(Blog blog) {
      Blog fl= new Blog();
      String sql = "INSERT INTO blog (id, date_blog, body, subject, blogger_email) VALUES (?,?,?,?,?)";
      try(PreparedStatement ps = connection.prepareStatement(sql)){
         ps.setInt(1, blog.getBlogId());
         ps.setDate(2, blog.getBlogDate());
         ps.setString(3, blog.getBlogBody());
         ps.setString(4, blog.getBlogSubject());
         ps.setString(5, blog.getBloggerEmail());
      	
         ps.executeUpdate();
         fl=blog;
      } catch (SQLException e) {
         System.out.println(e);
      } 
      return fl;
   }

@Override
   public int getLastId() {
      String sql = "SELECT * FROM Blog ORDER BY id DESC LIMIT 1;";
      try (PreparedStatement ps = connection.prepareStatement(sql)) {
         try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
               int id = rs.getInt("id");
               return id;
            }
         } catch (SQLException e){
            System.out.println(e);
         }
      } catch (SQLException e) {
         System.out.println(e);
      } 
      return -1;
   }
}

