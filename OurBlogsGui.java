package com.ourblogs;

import com.ourblogs.db.AccountDaoImpl;
import com.ourblogs.db.BlogDaoImpl;
import com.ourblogs.db.BloggerDaoImpl;
import com.ourblogs.db.InteractionDaoImpl;
import com.ourblogs.db.NotificationDaoImpl;
import com.ourblogs.db.TagDaoImpl;
import com.ourblogs.db.UserActivityDaoImpl;
import com.ourblogs.service.Service;
import com.ourblogs.ui.gui.MainFrame;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OurBlogsGui {
  public static void main(String[] args) {
    // Set look and feel to system default for better appearance
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Database connection setup
    SwingUtilities.invokeLater(() -> {
      try {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/ourblogs_db";
        String username = "root";
        String password = "manager";

        // Establish connection
        Connection connection = DriverManager.getConnection(url, username, password);

        // Initialize DAOs
        AccountDaoImpl accountDao = new AccountDaoImpl(connection);
        BloggerDaoImpl bloggerDao = new BloggerDaoImpl(connection);
        BlogDaoImpl blogDao = new BlogDaoImpl(connection);
        InteractionDaoImpl interactionDao = new InteractionDaoImpl(connection);
        UserActivityDaoImpl activityDao = new UserActivityDaoImpl(connection);
        NotificationDaoImpl notificationDao = new NotificationDaoImpl(connection);
        TagDaoImpl tagDao = new TagDaoImpl(connection);

        // Initialize Service with ALL 7 parameters
        Service service = new Service(accountDao, bloggerDao, blogDao, interactionDao,
            activityDao, notificationDao, tagDao);

        // Create and show main frame
        MainFrame mainFrame = new MainFrame(service);
        mainFrame.setVisible(true);

      } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            null,
            "Database connection failed: " + e.getMessage(),
            "Connection Error",
            JOptionPane.ERROR_MESSAGE
        );
        System.exit(1);
      }
    });
  }
}