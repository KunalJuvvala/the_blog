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
    Scanner scanner = new Scanner(System.in);
    Connection connection = null;

    try {
      String url = "jdbc:mysql://localhost:3306/ourblogs_db?useSSL=false&serverTimezone=UTC";

      String username;
      String password;

      System.out.println("Enter DB username (Hint: usually 'root'): ");
      username = scanner.nextLine();

      System.out.println("Enter DB password: ");
      password = scanner.nextLine();
      Class.forName("com.mysql.cj.jdbc.Driver");
      System.out.println("Connecting to database...");
      connection = DriverManager.getConnection(url, username, password);
      System.out.println("Connection successful!");
      AccountDao accountDao = new AccountDaoImpl(connection);
      BloggerDao bloggerDao = new BloggerDaoImpl(connection);
      BlogDao blogDao = new BlogDaoImpl(connection);
      Service service = new Service(accountDao, bloggerDao, blogDao);
      FrontController controller = new FrontController(service, scanner);
      controller.begin();

    } catch (ClassNotFoundException e) {
      System.out.println("!!! ERROR: MySQL Driver not found !!!");
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("!!! ERROR: Database Connection Failed !!!");
      System.out.println("Check your URL, Username, and Password.");
      e.printStackTrace();
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