package com.ourblogs.entity;
import java.sql.Timestamp;

public class BlogTag {
  private int blogId;
  private int tagId;
  private String taggerEmail;
  private Timestamp assignedDate;

  public int getBlogId() { return blogId; }
  public void setBlogId(int blogId) { this.blogId = blogId; }
  public int getTagId() { return tagId; }
  public void setTagId(int tagId) { this.tagId = tagId; }
  public String getTaggerEmail() { return taggerEmail; }
  public void setTaggerEmail(String taggerEmail) { this.taggerEmail = taggerEmail; }
  public Timestamp getAssignedDate() { return assignedDate; }
  public void setAssignedDate(Timestamp assignedDate) { this.assignedDate = assignedDate; }
}
