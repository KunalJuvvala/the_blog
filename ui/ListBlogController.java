package com.ourblogs.ui;

import com.ourblogs.entity.Blogger;
import com.ourblogs.service.Service;
import com.ourblogs.ui.view.ReadBlogsView;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListBlogController {

  private Service service;

  public ListBlogController(Service service) {
    this.service = service;
  }

  public void begin() {
    System.out.println("\n--- All Bloggers ---");
    try {
      ArrayList<Blogger> bloggers = service.getallBloggers();

      if (bloggers.isEmpty()) {
        System.out.println("No bloggers found in the database.");
      } else {
        ReadBlogsView.displayBloggerList(bloggers);
      }

    } catch (SQLException e) {
      System.out.println("Error fetching bloggers: " + e.getMessage());
      e.printStackTrace();
    }
  }
}