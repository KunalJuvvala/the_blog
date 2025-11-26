package com.ourblogs.entity;

public class Account {

  // Fields are now private and lowercase
  private String email;
  private String password;


  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}