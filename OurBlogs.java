package com.ourblogs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import com.ourblogs.db.AccountDaoImpl;
import com.ourblogs.db.BloggerDaoImpl;
import com.ourblogs.db.BlogDaoImpl;
import com.ourblogs.service.AccountDao;
import com.ourblogs.service.BloggerDao;
import com.ourblogs.service.BlogDao;
import com.ourblogs.service.Service;
import com.ourblogs.ui.FrontController;

public class OurBlogs {
  public static void main(String[] args) {
    // Cleaned up: You only need ONE scanner
    Scanner scanner = new Scanner(System.in);
    Connection connection = null;

    try {
      // CHECK: Does your MySQL Workbench say 'ourblogs_db' or just 'ourblogs'?
      // Make sure this matches exactly.
      String url = "jdbc:mysql://localhost:3306/ourblogs_db?useSSL=false&serverTimezone=UTC";

      String username;
      String password;

      System.out.println("Enter DB username (Hint: usually 'root'): ");
      username = scanner.nextLine();

      System.out.println("Enter DB password: ");
      password = scanner.nextLine();

      // Load the driver
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Try to connect
      System.out.println("Connecting to database...");
      connection = DriverManager.getConnection(url, username, password);
      System.out.println("Connection successful!");

      // Initialize layers
      AccountDao accountDao = new AccountDaoImpl(connection);
      BloggerDao bloggerDao = new BloggerDaoImpl(connection);
      BlogDao blogDao = new BlogDaoImpl(connection);
      Service service = new Service(accountDao, bloggerDao, blogDao);
      FrontController controller = new FrontController(service, scanner);

      // Start App
      controller.begin();

    } catch (ClassNotFoundException e) {
      System.out.println("!!! ERROR: MySQL Driver not found !!!");
      e.printStackTrace(); // <--- CRITICAL FIX
    } catch (SQLException e) {
      System.out.println("!!! ERROR: Database Connection Failed !!!");
      System.out.println("Check your URL, Username, and Password.");
      e.printStackTrace(); // <--- CRITICAL FIX
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}