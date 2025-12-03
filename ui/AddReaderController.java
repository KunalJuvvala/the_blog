/*
package com.ourblogs.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blogger;
import com.ourblogs.service.Service;
import com.ourblogs.ui.view.AddReaderView;

public class AddReaderController {

  private LoginController loginController;
  private Service service;
  private Scanner scanner;
  private Account login;

  public AddReaderController(LoginController loginController, Service service, Scanner scanner, Account login) {
    this.loginController = loginController;
    this.service = service;
    this.scanner = scanner;
    this.login = login;
  }


  public String begin() throws SQLException {
    String loginemail = login.getEmail();
    Blogger loggedInBlogger = this.service.getBloggerByEmail(loginemail); // Instance call

    // Check if logged-in user is public (logic from old controller)
    if ("true".equals(loggedInBlogger.getisPublic())) {
      return "All bloggers are readers.";
    }

    ArrayList<Blogger> allBloggers = this.service.getallBloggers(); // Instance call
    ArrayList<Blogger> potentialReaders = new ArrayList<>();

    // Filter out the logged-in blogger
    for (Blogger blogger : allBloggers) {
      if (!loggedInBlogger.getBloggerEmailAddress().equals(blogger.getBloggerEmailAddress())) {
        potentialReaders.add(blogger);
      }
    }

    // *** LOGIC MOVED FROM ADDREADERVIEW ***
    // The controller now manages the loop and service calls
    AddReaderView.displayBloggerList(potentialReaders);

    boolean finishedAdding = false;
    while (!finishedAdding) {
      int lineNumber = AddReaderView.getUserSelection(scanner, potentialReaders.size());

      if (lineNumber == 0) {
        finishedAdding = true;
        continue;
      }

      Blogger selectedBlogger = potentialReaders.get(lineNumber - 1);

      // Check if already friends (Instance call)
      int flag = this.service.isFriends(selectedBlogger.getBloggerEmailAddress(), loginemail);

      if (flag == 1) {
        System.out.println(selectedBlogger.getBloggerName() + " is already a reader.");
      } else {
        // Add friend (Instance call)
        this.service.addFriend(selectedBlogger, loginemail);
        System.out.println(selectedBlogger.getBloggerName() + " added as reader.");
      }
    }

    return "Readers updated successfully.\n";
  }
}*/
