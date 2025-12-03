/*
package com.ourblogs;

import static javax.swing.text.html.HTML.Tag.HEAD;

import com.ourblogs.db.InteractionDaoImpl;
import com.ourblogs.db.NotificationDaoImpl;
import com.ourblogs.db.TagDaoImpl;
import com.ourblogs.db.UserActivityDaoImpl;
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
      String url = "jdbc:mysql://localhost:3306/ourblogs_db?useSSL=false&serverTimezone=UTC";

      String username;
      String password;
      Scanner Scanner = new Scanner(System.in);
      System.out.println("Enter DB username: ");
      username = Scanner.nextLine();
      System.out.println("Enter DB password: ");
      password = Scanner.nextLine();
      Class.forName("com.mysql.cj.jdbc.Driver");

      connection = DriverManager.getConnection(url, username, password);
      AccountDao accountDao = new AccountDaoImpl(connection);
      BloggerDao bloggerDao = new BloggerDaoImpl(connection);
      BlogDao blogDao = new BlogDaoImpl(connection);
      InteractionDao interactionDao = new InteractionDaoImpl(connection);

      UserActivityDaoImpl activityDao = new UserActivityDaoImpl(connection);
      NotificationDaoImpl notificationDao = new NotificationDaoImpl(connection);
      TagDaoImpl tagDao = new TagDaoImpl(connection);

      Service service = new Service(accountDao, bloggerDao, blogDao, interactionDao,
          activityDao, notificationDao, tagDao);
      FrontController controller = new FrontController(service, scanner);
      controller.begin();
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      connection = null;
    }
  }
}*/
