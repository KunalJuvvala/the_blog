package com.ourblogs.entity;
import java.sql.Timestamp;

public class UserActivity {
  private int activityId;
  private String userEmail;
  private String activityType;
  private Timestamp activityDate;
  private String relatedEntity;

  public int getActivityId() { return activityId; }
  public void setActivityId(int activityId) { this.activityId = activityId; }
  public String getUserEmail() { return userEmail; }
  public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
  public String getActivityType() { return activityType; }
  public void setActivityType(String activityType) { this.activityType = activityType; }
  public Timestamp getActivityDate() { return activityDate; }
  public void setActivityDate(Timestamp activityDate) { this.activityDate = activityDate; }
  public String getRelatedEntity() { return relatedEntity; }
  public void setRelatedEntity(String relatedEntity) { this.relatedEntity = relatedEntity; }
}