package com.ourblogs.entity;

import java.sql.Date;


public class Blog {
  // Fields are now private
  private int blogId;
  private String blogBody, blogSubject, bloggerEmail;
  private Date blogDate; // Changed from default access to private

  public int getBlogId() {
    return blogId;
  }

  public void setBlogId(int blogId) {
    this.blogId = blogId;
  }


  public String getBlogBody() {
    return blogBody;
  }

  public void setBlogBody(String blogBody) {
    this.blogBody = blogBody;
  }


  public Date getBlogDate() {
    return blogDate;
  }

  public void setBlogDate(Date blogDate2) {
    this.blogDate = blogDate2;
  }

  public String getBlogSubject() {
    return blogSubject;
  }

  public void setBlogSubject(String blogSubject) {
    this.blogSubject = blogSubject;
  }

  public String getBloggerEmail() {
    return bloggerEmail;
  }

  public void setBloggerEmail(String bloggerEmail) {
    this.bloggerEmail = bloggerEmail;
  }


}