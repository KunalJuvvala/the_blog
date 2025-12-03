/*
package com.ourblogs.ui;

import java.util.Scanner;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blogger;
import com.ourblogs.entity.BloggerDTO;
import com.ourblogs.service.Service;
import com.ourblogs.ui.view.RegisterView;

public class RegisterController {
  FrontController frontController;
  Scanner scanner;

  Service accountService;


  public RegisterController(FrontController frontController, Service accountService, Scanner scanner) {
    this.frontController = frontController;
    this.accountService = accountService;
    this.scanner = scanner;
  }

  private Account getAccount(BloggerDTO bldt) {
    Account acc= new Account();
    acc.setEmail(bldt.getEmail());
    acc.setPassword(bldt.getPassword());
    return acc;
  }

  private Blogger getBlogger(BloggerDTO bldt) {
    Blogger bl= new Blogger();
    bl.setBloggerEmailAddress(bldt.getEmail());
    bl.setBloggerName(bldt.getBloggerName());
    bl.setisPublic(bldt.getisPublic());
    return bl;
  }
  public void begin() {
    BloggerDTO bldt = null;
    boolean registered = false;
    Account reg= new Account();
    String message = "User Registration:";
    while (!registered) {
      bldt = RegisterView.acceptUserAccount(message, scanner);
      if (bldt == null) {
        message = "Invalid input for public/private. Try again:";
        continue; // Loop again for re-prompt
      }
      Account acc=getAccount(bldt);
      Blogger bl=getBlogger(bldt);

      // FIX: Calling the instance method using 'this.accountService'
      reg = this.accountService.register(acc,bl);

      if (reg != null) {
        registered = true;
        System.out.println("Registration successful!");
      }
      else {
        message = "Registration failed. Account may already exist.";
      }
    }
  }
}*/
