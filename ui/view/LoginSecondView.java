package com.ourblogs.ui.view;

import java.util.Scanner;

import com.ourblogs.entity.Account;

public class LoginSecondView {
  public static void displayBlogMenu(String message) {
    System.out.println("1 Create Blog");
    System.out.println("2 View Blogs");
    System.out.println("3 Add Readers");
    System.out.println("4 Read Blogs");
    System.out.println("5 Edit Blogs");
    System.out.println("6 Delete Blogs");
    System.out.println("7 Exit");
  }

  public static int getUserChoice(Scanner scanner) {
    try {
      return Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
      return -1;
    }
  }
}

