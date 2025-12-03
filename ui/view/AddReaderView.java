/*
package com.ourblogs.ui.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.ourblogs.entity.Blogger;

public class AddReaderView {

  public static void displayBloggerList(ArrayList<Blogger> potentialReaders) {
    for (int i = 0; i < potentialReaders.size(); i++) {
      Blogger blogger = potentialReaders.get(i);
      System.out.println((i + 1) + ". " + blogger.getBloggerName() + " (" + blogger.getBloggerEmailAddress() + ")");
    }
    System.out.println("---");
  }

  // New method to handle input only
  public static int getUserSelection(Scanner scanner, int maxIndex) {
    while (true) {
      System.out.print("Enter the number from the blogger list to add as a reader (press 0 to finish): ");
      String input = scanner.nextLine();
      if (input.isEmpty()) {
        System.out.println("Invalid input. Please enter a line number.");
        continue;
      }
      int lineNumber;
      try {
        lineNumber = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid integer line number.");
        continue;
      }
      if (lineNumber == 0) {
        return 0; // Signal to finish
      }
      if (lineNumber < 1 || lineNumber > maxIndex) {
        System.out.println("Invalid line number.");
        continue;
      }
      return lineNumber; // Valid selection
    }
  }
}*/
