/*
package com.ourblogs.ui.view;

import java.util.Scanner;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blog;


public class PostBlogView {
	
   public static Blog postBlog(String message, Scanner scanner, Account login) {
      System.out.println(message);
      Blog blog = new Blog();
      System.out.print("\nEnter blog subject: ");
      blog.setBlogSubject(scanner.nextLine());
      System.out.print("\nEnter blog body: ");
      blog.setBlogBody(scanner.nextLine());
      long millis=System.currentTimeMillis();  
      java.sql.Date date = new java.sql.Date(millis);      
      blog.setBlogDate(date);
      blog.setBloggerEmail(login.getEmail());
      return blog;
   }
}
	


*/
