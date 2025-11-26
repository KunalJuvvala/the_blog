package com.ourblogs.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blogger;
import com.ourblogs.service.BloggerDao;

public class BloggerDaoImpl implements BloggerDao {
   private Connection connection;
   public BloggerDaoImpl(Connection connection) {
      this.connection=connection;
   }
	
   public ArrayList<Blogger> getAllBloggers(){
      ArrayList<Blogger> bloggers = new ArrayList<>();
      String sql = "SELECT * FROM blogger";
      try (PreparedStatement ps = connection.prepareStatement(sql)) {
         try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
               Blogger acc = new Blogger();
               String email = rs.getString("email_address");
               String name = rs.getString("name");
               String ispub = rs.getString("isPublic");
               acc.setBloggerEmailAddress(email);
               acc.setBloggerName(name);
               acc.setisPublic(ispub);
               bloggers.add(acc);
            }
         }
      } catch (SQLException e) {
         System.out.println(e);
      
      } 
      return bloggers;
   }
	
   public Blogger getBloggerByEmail(String email) {
      String sql = "SELECT * FROM blogger WHERE email_address = ?";
      try (PreparedStatement ps = connection.prepareStatement(sql)) {
         ps.setString(1, email);
         try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
               Blogger acc = new Blogger();
               String name = rs.getString("name");
               String ispub = rs.getString("isPublic");
               acc.setBloggerEmailAddress(email);
               acc.setBloggerName(name);
               acc.setisPublic(ispub);
               return acc;
            } else {
               return null;
            }
         }catch (SQLException e){
            System.out.println(e);
         }
      } catch (SQLException e) {
         System.out.println(e);
      } 
      return null;
   }
	
   public Blogger insertBlogger(Blogger blogger) {
      String sql = "INSERT INTO blogger(email_address, name, isPublic) VALUES (?,?,?)";
      try (PreparedStatement ps = connection.prepareStatement(sql)) {
         ps.setString(1, blogger.getBloggerEmailAddress());
         ps.setString(2, blogger.getBloggerName());
         ps.setString(3, blogger.getisPublic());
         ps.executeUpdate();
      } catch (SQLException e) {
         System.out.println(e);
      } 
      return blogger;
   }
   @Override
   
   public void insertReader(Blogger blogger, String loginemail) {
      String sql = "INSERT INTO Blogger_Reader(blogger_email, reader_email) VALUES (?,?)";
      try (PreparedStatement ps = connection.prepareStatement(sql)) {
         ps.setString(1, loginemail);
         ps.setString(2, blogger.getBloggerEmailAddress());
         ps.executeUpdate();
      } catch (SQLException e) {
         System.out.println(e);
      } 
   }

   @Override
   public int checkfriends(String loginemail, String bloggerEmailAddress) {
   	
      String sql = "SELECT * FROM Blogger_Reader WHERE blogger_email = ? AND reader_email = ?";
      int flag=0;
      try (PreparedStatement ps = connection.prepareStatement(sql)) {
         ps.setString(1, bloggerEmailAddress);
         ps.setString(2, loginemail);
         try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
               flag=1;
            } else {
               flag=0;
            }
         } catch (SQLException e){
            System.out.println(e);
         }
              
      } catch (SQLException e) {
         System.out.println(e);
      } 
      return flag;
   	
   }

	
}

