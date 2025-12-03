package com.ourblogs.ui;

import java.util.ArrayList;
import java.util.Scanner;
import com.ourblogs.entity.Notifications;
import com.ourblogs.service.Service;

public class NotificationController {
  private Service service;
  private Scanner scanner;
  private String userEmail;

  public NotificationController(Service service, Scanner scanner, String userEmail) {
    this.service = service;
    this.scanner = scanner;
    this.userEmail = userEmail;
  }

  public void begin() {
    System.out.println("\n--- Your Notifications ---");
    // You need to expose getUnreadNotifications in Service.java first!
    // Assuming you added: public ArrayList<Notification> getNotifications(String email) { ... }
    ArrayList<Notifications> list = service.getNotifications(userEmail);

    if (list.isEmpty()) {
      System.out.println("No new notifications.");
    } else {
      for (Notifications n : list) {
        System.out.println("[" + n.getNotificationDate() + "] " + n.getNotificationText());
      }
    }
  }
}