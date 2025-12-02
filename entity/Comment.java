package com.ourblogs.entity;

import java.sql.Timestamp;

public class Comment {
  private int commentId;
  private int blogId;
  private String commenterEmail;
  private String commentText;
  private Timestamp commentDate;

  public int getCommentId() {
    return commentId;
  }

  public void setCommentId(int commentId) {
    this.commentId = commentId;
  }

  public int getBlogId() {
    return blogId;
  }

  public void setBlogId(int blogId) {
    this.blogId = blogId;
  }

  public String getCommenterEmail() {
    return commenterEmail;
  }

  public void setCommenterEmail(String commenterEmail) {
    this.commenterEmail = commenterEmail;
  }

  public String getCommentText() {
    return commentText;
  }

  public void setCommentText(String commentText) {
    this.commentText = commentText;
  }

  public Timestamp getCommentDate() {
    return commentDate;
  }

  public void setCommentDate(Timestamp commentDate) {
    this.commentDate = commentDate;
  }
}