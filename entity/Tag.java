package com.ourblogs.entity;
import java.sql.Date;

public class Tag {
  private int tagId;
  private String tagName;
  private Date createdDate;
  private String tagDescription;

  public int getTagId() { return tagId; }
  public void setTagId(int tagId) { this.tagId = tagId; }
  public String getTagName() { return tagName; }
  public void setTagName(String tagName) { this.tagName = tagName; }
  public Date getCreatedDate() { return createdDate; }
  public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
  public String getTagDescription() { return tagDescription; }
  public void setTagDescription(String tagDescription) { this.tagDescription = tagDescription; }
}