package com.ourblogs.ui;
import java.sql.SQLException;
import java.util.Scanner;

import com.ourblogs.entity.Account;
import com.ourblogs.service.Service;
import com.ourblogs.ui.view.MenuView;


public class FrontController {
   public static String BANNER = "Welcome to OurBlogs!  ";
   public static int EXIT = 3;
   public static int REGISTER = 1;
   public static int LOGIN = 2;
	
   private Scanner scanner;
   private Service service;
	
   private Account loggedInAccount = null;
	
   public FrontController(Service service, Scanner scanner) {
      this.service = service;
      this.scanner = scanner;
   }
	
   public void begin() throws SQLException {
      String message = BANNER;
      MenuView.displayMenu(message);
      int choice = MenuView.getUserChoice(scanner);
      while (choice != EXIT) {
         if (choice == REGISTER) {
            RegisterController controller = new RegisterController(this, service, scanner);
            controller.begin();
         }
         else if (choice == LOGIN) {
            LoginController controller = new LoginController(this, service, scanner);
            controller.begin();
         
         }
         MenuView.displayMenu(message);
         choice = MenuView.getUserChoice(scanner);
      }
   }

   public void setAccount(Account login2) {
      this.loggedInAccount = login2; 
   }
}
