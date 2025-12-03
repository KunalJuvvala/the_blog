package com.ourblogs.entity;
import java.sql.Timestamp;

public class Notifications {
  private int notificationId;
  private String recipientEmail;
  private String notificationType;
  private String notificationText;
  private Timestamp notificationDate;
  private boolean isRead;

  public int getNotificationId() { return notificationId; }
  public void setNotificationId(int notificationId) { this.notificationId = notificationId; }
  public String getRecipientEmail() { return recipientEmail; }
  public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }
  public String getNotificationType() { return notificationType; }
  public void setNotificationType(String notificationType) { this.notificationType = notificationType; }
  public String getNotificationText() { return notificationText; }
  public void setNotificationText(String notificationText) { this.notificationText = notificationText; }
  public Timestamp getNotificationDate() { return notificationDate; }
  public void setNotificationDate(Timestamp notificationDate) { this.notificationDate = notificationDate; }
  public boolean isRead() { return isRead; }
  public void setRead(boolean read) { isRead = read; }
}