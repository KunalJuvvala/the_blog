package com.ourblogs.ui.view;
import java.util.Scanner;


import com.ourblogs.entity.BloggerDTO;

public class RegisterView {
   public static BloggerDTO acceptUserAccount(String message, Scanner scanner) {
      String scannerInput; String trueInput="true"; String falseInput="false";
      System.out.println(message);
      BloggerDTO account = new BloggerDTO();
      System.out.print("\nPlease enter your email, then press Enter: ");
      account.setEmail(scanner.nextLine());
      System.out.print("\nPlease enter your password, then press Enter: ");
      account.setPassword(scanner.nextLine());
      System.out.print("\nPlease enter your name, then press Enter: ");
      account.setBloggerName(scanner.nextLine());
      System.out.print("\nPlease enter true for a public account, or enter false for a private account then press Enter: ");
      scannerInput =scanner.nextLine();
      if( scannerInput.equals(trueInput)|| scannerInput.equals(falseInput))  {
         account.setisPublic(scannerInput);
         return account;
      }else {
         System.out.print("\n Wrong Input ");
         return null;
      }
   }
}
