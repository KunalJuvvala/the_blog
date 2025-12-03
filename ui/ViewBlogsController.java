/*
package com.ourblogs.ui;
import java.util.ArrayList;
import java.util.Scanner;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blog;
import com.ourblogs.service.Service;
import com.ourblogs.ui.view.insertedBlogview;


public class ViewBlogsController {
   FrontController frontController;
   Scanner scanner;
   LoginController loginController;
   Account login;
		
   Service service;
   public ViewBlogsController(LoginController loginController, Service service, Scanner scanner, Account login) {
      this.loginController = loginController;
      this.service = service;
      this.scanner = scanner;
      this.login = login;
   }

   // FIXED FROM LAST ASSIGNMENT
   */
/* View Controller is now passing the list of blogs,
    * instead of a concatenated string to InsertedBlogView
    *//*

   public String begin() {
     ArrayList<Blog> allblogs = this.service.viewBlogsbyEmail(login.getEmail()); // Using instance call
     String message = "All Blogs from " + login.getEmail();
     return insertedBlogview.InstertedBlogView(message, allblogs);
   }
}
*/
