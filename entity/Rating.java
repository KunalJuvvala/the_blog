package com.ourblogs.entity;

import java.sql.Timestamp;

public class Rating {
  private int blogId;
  private String userEmail;
  private int ratingValue;
  private Timestamp ratingDate;

  public int getBlogId() {
    return blogId;
  }

  public void setBlogId(int blogId) {
    this.blogId = blogId;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public int getRatingValue() {
    return ratingValue;
  }

  public void setRatingValue(int ratingValue) {
    this.ratingValue = ratingValue;
  }

  public Timestamp getRatingDate() {
    return ratingDate;
  }

  public void setRatingDate(Timestamp ratingDate) {
    this.ratingDate = ratingDate;
  }
}