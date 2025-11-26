package com.ourblogs.entity;

public class Blogger {
  // Fields are now private
  private String bloggerEmailAddress;
  private String bloggerName;
  private String isPublic;

  public String getBloggerEmailAddress() {
    return bloggerEmailAddress;
  }

  public String getBloggerName() {
    return bloggerName;
  }

  public String getisPublic() {
    return isPublic;
  }

  public void setBloggerEmailAddress(String bloggerEmail) {
    this.bloggerEmailAddress = bloggerEmail;
  }

  public void setBloggerName(String bloggerSetName) {
    this.bloggerName = bloggerSetName;
  }

  public void setisPublic(String isPublic) {
    this.isPublic = isPublic;
  }
}