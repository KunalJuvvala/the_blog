package com.ourblogs.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blog;
import com.ourblogs.entity.Blogger;
import com.ourblogs.service.Service;
import com.ourblogs.ui.view.ReadBlogsView;

public class ReadBlogController {

  private LoginController loginController;
  private Service service;
  private Scanner scanner;
  private Account login;


  public ReadBlogController(LoginController loginController, Service service, Scanner scanner, Account login) {
    this.loginController = loginController;
    this.service = service;
    this.scanner = scanner;
    this.login = login;
  }


  public String begin() throws SQLException {

    ArrayList<Blogger> bloggers = this.service.getallBloggers(); // Instance call
    ArrayList<Blogger> potentialReaders = new ArrayList<>();

    // Filter out the logged-in user
    for (Blogger blogger : bloggers) {
      if (!login.getEmail().equals(blogger.getBloggerEmailAddress())) {
        potentialReaders.add(blogger);
      }
    }

    // *** LOGIC MOVED FROM READBLOGSVIEW ***
    ReadBlogsView.displayBloggerList(potentialReaders);

    // Get selection from the view
    int bloggerIndex = ReadBlogsView.getUserSelection(scanner, potentialReaders.size());

    if (bloggerIndex >= 0 && bloggerIndex < potentialReaders.size()) {
      Blogger selectedBlogger = potentialReaders.get(bloggerIndex);
      ArrayList<Blog> blogs = new ArrayList<>();
      boolean canRead = false;

      // Check if public (true is stored as String "true")
      if ("true".equals(selectedBlogger.getisPublic())) {
        canRead = true;
      } else {
        // Check if friends (Instance call)
        int flag = this.service.isFriends(login.getEmail(), selectedBlogger.getBloggerEmailAddress());
        if (flag == 1) {
          canRead = true;
        }
      }

      if (canRead) {
        // Fetch blogs (Instance call)
        blogs = this.service.viewBlogsbyEmail(selectedBlogger.getBloggerEmailAddress());
        ReadBlogsView.displayBlogs(selectedBlogger.getBloggerName(), blogs);
      } else {
        System.out.println("Sorry, you do not have permission to read blogs by " + selectedBlogger.getBloggerName());
      }
    } else {
      System.out.println("Invalid blogger line number");
    }

    return null;
  }
}