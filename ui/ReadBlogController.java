/*
package com.ourblogs.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blog;
import com.ourblogs.entity.Blogger;
import com.ourblogs.entity.Comment;
import com.ourblogs.entity.Rating;
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

  public void begin() throws SQLException {
    ArrayList<Blogger> bloggers = this.service.getallBloggers();
    ArrayList<Blogger> potentialReaders = new ArrayList<>();

    for (Blogger blogger : bloggers) {
      if (!login.getEmail().equals(blogger.getBloggerEmailAddress())) {
        potentialReaders.add(blogger);
      }
    }

    ReadBlogsView.displayBloggerList(potentialReaders);
    int bloggerIndex = ReadBlogsView.getUserSelection(scanner, potentialReaders.size());

    if (bloggerIndex >= 0 && bloggerIndex < potentialReaders.size()) {
      Blogger selectedBlogger = potentialReaders.get(bloggerIndex);

      // Check permissions (Public or Friends)
      boolean canRead = "true".equals(selectedBlogger.getisPublic());
      if (!canRead) {
        int flag = this.service.isFriends(login.getEmail(), selectedBlogger.getBloggerEmailAddress());
        if (flag == 1) canRead = true;
      }

      if (canRead) {
        ArrayList<Blog> blogs = this.service.viewBlogsbyEmail(selectedBlogger.getBloggerEmailAddress());
        // Display the blogs
        ReadBlogsView.displayBlogs(selectedBlogger.getBloggerName(), blogs);

        // --- NEW INTERACTION LOGIC START ---
        if (!blogs.isEmpty()) {
          handleInteractions(blogs);
        }
        // --- NEW INTERACTION LOGIC END ---

      } else {
        System.out.println("Permission denied: You must be a friend to read these blogs.");
      }
    } else {
      System.out.println("Invalid selection.");
    }
  }

  private void handleInteractions(ArrayList<Blog> blogs) {
    System.out.println("\n--- Interactions ---");
    System.out.println("Enter the Blog ID you want to Rate or Comment on (or 0 to go back): ");
    String input = scanner.nextLine();

    try {
      int blogId = Integer.parseInt(input);
      if (blogId == 0) return;

      // Verify the ID is in the list we just showed
      boolean found = false;
      for(Blog b : blogs) { if(b.getBlogId() == blogId) found = true; }

      if(found) {
        System.out.println("1. Add Comment");
        System.out.println("2. Rate (1-5)");
        String choice = scanner.nextLine();

        if(choice.equals("1")) {
          System.out.println("Enter your comment:");
          String text = scanner.nextLine();
          Comment c = new Comment();
          c.setBlogId(blogId);
          c.setCommenterEmail(login.getEmail());
          c.setCommentText(text);
          // Call Service (Service needs to call InteractionDao)
          service.addComment(c);
          System.out.println("Comment added!");

        } else if (choice.equals("2")) {
          System.out.println("Enter rating (1-5):");
          int stars = Integer.parseInt(scanner.nextLine());
          if(stars < 1 || stars > 5) {
            System.out.println("Invalid rating.");
          } else {
            Rating r = new Rating();
            r.setBlogId(blogId);
            r.setUserEmail(login.getEmail());
            r.setRatingValue(stars);
            service.addRating(r);
            System.out.println("Rating submitted!");
          }
        }
      } else {
        System.out.println("Invalid Blog ID from this list.");
      }
    } catch (NumberFormatException e) {
      System.out.println("Invalid input.");
    }
  }
}*/
