/*
package com.ourblogs.ui;

import com.ourblogs.ui.view.DeleteBlogController;
import java.sql.SQLException;
import java.util.Scanner;

import com.ourblogs.entity.Account;
import com.ourblogs.service.Service;
import com.ourblogs.ui.view.LoginSecondView;
import com.ourblogs.ui.view.LoginView;
import com.ourblogs.ui.view.MenuView;
import com.ourblogs.ui.view.insertedBlogview;


public class LoginController {
   FrontController frontController;
   Scanner scanner;
   public static int EXIT = 9;
  public static int NOTIFICATIONS = 8;
   public static int LIST_BLOGGERS = 7;
   public static int EDIT = 5;
   public static int DELETE = 6;
   public static int POST = 1;
   public static int VIEW = 2;
   public static int ADDRDR = 3;
   public static int RDBL = 4;
	
   Service service;
   public LoginController(FrontController frontController, Service service, Scanner scanner) {
      this.frontController = frontController;
      this.service = service;
      this.scanner = scanner;
   }

   public void begin() throws SQLException {
      Account login = null;
      boolean loggedin = false;
      boolean login1 = false;
   	  String msg1,msg2,msg3;
      String message = "Log In";
      while (!loggedin) {
         login = LoginView.acceptUserLogin(message, scanner);
         login1 = service.authenticate(login);
         if (login1 != false && login!=null) {
            loggedin = true;
            frontController.setAccount(login);
            LoginSecondView.displayBlogMenu(message);
            int choice1 = LoginSecondView.getUserChoice(scanner);
            while (choice1!=EXIT) {
               if(choice1 == POST) {
                  PostController controller1 = new PostController(this, service, scanner, login);
                  msg1=controller1.begin();
                  insertedBlogview.dispmsg(msg1);
               }
               else if(choice1 == VIEW) {
                  ViewBlogsController controller1 = new ViewBlogsController(this, service, scanner, login);
                  msg2=controller1.begin();
                  insertedBlogview.dispmsg(msg2);
               }
               else if(choice1 == ADDRDR) {
                   AddReaderController controller1 = new AddReaderController(this, service, scanner, login);
                   msg3=controller1.begin();
                   insertedBlogview.dispmsg(msg3);
                }
               else if(choice1 == RDBL) {
                   ReadBlogController controller1 = new ReadBlogController(this, service, scanner, login);
                   controller1.begin();
                }
               else if(choice1 == EDIT) {
                 EditBlogController controller1 = new EditBlogController(service, scanner, login);
                 String msg = controller1.beginEditBlog();
                 insertedBlogview.dispmsg(msg);
               }
               else if(choice1 == DELETE) {
                 DeleteBlogController controller1 = new DeleteBlogController(service, scanner);
                 String msg = controller1.begin();
                 insertedBlogview.dispmsg(msg);
               }
               else if (choice1 == LIST_BLOGGERS) {
                 ListBlogController controller = new ListBlogController(service);
                 controller.begin();
               }
               else if (choice1 == NOTIFICATIONS) {
                 NotificationController nc = new NotificationController(service, scanner, login.getEmail());
                 nc.begin();
               }
               LoginSecondView.displayBlogMenu(message);
               choice1 = MenuView.getUserChoice(scanner);	
            }
         }
         else {
            message = "authentication failed";
         }
      }
   }
}
*/
