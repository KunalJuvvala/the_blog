/*
package com.ourblogs.ui.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.ourblogs.entity.Blogger;
import com.ourblogs.entity.Blog;

public class ReadBlogsView {

  public static void displayBloggerList(ArrayList<Blogger> bloggers) {
    System.out.println("\nList of Bloggers:");
    for (int i = 0; i < bloggers.size(); i++) {
      Blogger blogger = bloggers.get(i);
      System.out.println((i + 1) + ". " + blogger.getBloggerEmailAddress() + " - " + blogger.getBloggerName());
    }
  }

  public static int getUserSelection(Scanner scanner, int maxIndex) {
    System.out.print("Enter the number from the blogger list you want to read the blogs of: ");
    // Use nextLine and parseInt to handle input buffer issues safely
    String input = scanner.nextLine();
    try {
      return Integer.parseInt(input) - 1; // Return 0-indexed selection
    } catch (NumberFormatException e) {
      return -1; // Indicate invalid input
    }
  }

  public static void displayBlogs(String bloggerName, ArrayList<Blog> blogs) {
    System.out.println("\nBlogs by " + bloggerName + ":");
    if (blogs == null || blogs.isEmpty()) {
      System.out.println("No blogs found.");
      return;
    }
    for (Blog blog : blogs) {
      System.out.println("Blog date: " + blog.getBlogDate() + " || " + "Subject: " +
          blog.getBlogSubject() + " || " + "Body: " +blog.getBlogBody());
    }
    System.out.println();
  }
}*/
