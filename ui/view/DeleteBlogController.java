package com.ourblogs.ui.view;

import com.ourblogs.service.Service;
import java.util.Scanner;

public class DeleteBlogController {
  private Service service;
  private Scanner scanner;

  public DeleteBlogController(Service service, Scanner scanner) {
    this.service = service;
    this.scanner = scanner;
  }

  public String begin() {
    System.out.print("\n--- Delete Blog ---");
    System.out.print("Enter the Blog ID you want to delete: ");
    String idString = scanner.nextLine();

    try {
      int blogId = Integer.parseInt(idString);
      boolean success = service.deleteBlog(blogId);
      if (success) {
        return "Success: Blog deleted!";
      } else {
        return "Failed: Blog ID not found!";
      }

    } catch (NumberFormatException e) {
      return "Invalid ID!";
    }
  }

}
