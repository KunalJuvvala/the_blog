/*
package com.ourblogs.ui;

import java.util.Scanner;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blog;
import com.ourblogs.service.Service;

import com.ourblogs.ui.view.PostBlogView;
import com.ourblogs.ui.view.insertedBlogview;

public class PostController {
   FrontController frontController;
   Scanner scanner;
   LoginController loginController;
   Account login;
		
   Service service;
   public PostController(LoginController loginController, Service service, Scanner scanner, Account login) {
      this.loginController = loginController;
      this.service = service;
      this.scanner = scanner;
      this.login = login;
   }


   public String begin() {
      Blog bl = null;
      Blog insertedBlog = new Blog();
      String message = "Enter Blog Details";
      String msg1;
      bl = PostBlogView.postBlog(message, scanner, login);
      insertedBlog = service.insertBlog(bl);
      if (insertedBlog!=null) {

      msg1 = "Inserted with attributes \n Id = "+insertedBlog.getBlogId()+"\n "
      		+ "Subject = "+insertedBlog.getBlogSubject()+"\n Body = "+insertedBlog.getBlogBody()+"\n "
      				+ "Date = "+insertedBlog.getBlogDate()+"\n Email = "+insertedBlog.getBloggerEmail();

        System.out.println("Enter tags: ");
        String tagInput = scanner.nextLine();

        if (!tagInput.trim().isEmpty()) {
          String[] tags = tagInput.split(",");
          for (String tag : tags) {
            service.addTagToBlog(insertedBlog.getBlogId(), tag.trim(), login.getEmail());
          }
        }
        return "Blog posted successfully with tags!";
      }
      else
      {msg1 = "Not Inserted";}
      
      return msg1;
   
   }
}

*/
