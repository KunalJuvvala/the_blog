package com.ourblogs;

import static javax.swing.text.html.HTML.Tag.HEAD;

import com.ourblogs.db.InteractionDaoImpl;
import com.ourblogs.service.InteractionDao;
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
    Scanner scanner = new Scanner(System.in);
    Connection connection = null;
    try {
      // --- CHANGE 1: JDBC URL for MySQL ---
      // Replace [host], [port], and [database_name] with your actual details
      String url = "jdbc:mysql://localhost:3306/ourblogs_db?useSSL=false&serverTimezone=UTC";

      String username;
      String password;
      Scanner Scanner = new Scanner(System.in);
      System.out.println("Enter DB username: ");
      username = Scanner.nextLine();
      System.out.println("Enter DB password: ");
      password = Scanner.nextLine();

      // --- CHANGE 2: JDBC Driver Class for MySQL ---
      Class.forName("com.mysql.cj.jdbc.Driver");

      connection = DriverManager.getConnection(url, username, password);
      AccountDao accountDao = new AccountDaoImpl(connection);
      BloggerDao bloggerDao = new BloggerDaoImpl(connection);
      BlogDao blogDao = new BlogDaoImpl(connection);
      InteractionDao interactionDao = new InteractionDaoImpl(connection);
      Service service = new Service(accountDao, bloggerDao, blogDao, interactionDao);
      FrontController controller = new FrontController(service, scanner);
      controller.begin();
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      // Cleaned up: You only need ONE scanner
      connection = null;

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
        InteractionDao interactionDao = new InteractionDaoImpl(connection);
        Service service = new Service(accountDao, bloggerDao, blogDao, interactionDao);
        FrontController controller = new FrontController(service, scanner);

        // Start App
        controller.begin();

      } catch (ClassNotFoundException h) {
        System.out.println("!!! ERROR: MySQL Driver not found !!!");
        h.printStackTrace(); // <--- CRITICAL FIX
      } catch (SQLException f) {
        System.out.println("!!! ERROR: Database Connection Failed !!!");
        System.out.println("Check your URL, Username, and Password.");
        f.printStackTrace(); // <--- CRITICAL FIX
      } finally {
        try {
          if (connection != null) {
            connection.close();
          }
        } catch (SQLException g) {
          g.printStackTrace();
        }
      }
    }
  }
}