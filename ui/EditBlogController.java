package com.ourblogs.ui;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blog;
import com.ourblogs.service.BlogDao;
import com.ourblogs.service.Service;
import java.sql.Date;
import java.util.Scanner;

public class EditBlogController {
  private Service service;
  private Scanner scanner;
  private Account login;

  public EditBlogController(Service service, Scanner scanner, Account login) {
    this.service = service;
    this.login = login;
    this.scanner = scanner;
  }

  public String beginEditBlog() {
    System.out.println("\n--- Edit Blog ---");
    System.out.print("Enter the ID of the blog you want to update: ");
    String idStr = scanner.nextLine();

    try {
      int blogId = Integer.parseInt(idStr);
      Blog blogToUpdate = new Blog();
      blogToUpdate.setBlogId(blogId);
      blogToUpdate.setBloggerEmail(login.getEmail());

      System.out.println("Enter new title: ");
      blogToUpdate.setBlogSubject(scanner.nextLine());

      System.out.println("Enter new content: ");
      blogToUpdate.setBlogBody(scanner.nextLine());

      long millis = System.currentTimeMillis();
      blogToUpdate.setBlogDate(new Date(millis));

      boolean success = service.updateBlog(blogToUpdate);
      if (success) {
        return "Blog updated successfully";
      } else {
        return "Failed to update blog!";
      }
    } catch (NumberFormatException e) {
      return "Invalid ID!";
    }
  }

}
