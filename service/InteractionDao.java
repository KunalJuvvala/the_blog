package com.ourblogs.service;

import java.util.ArrayList;
import com.ourblogs.entity.Comment;
import com.ourblogs.entity.Rating;

public interface InteractionDao {
  void addComment(Comment comment);

  void addRating(Rating rating);

  ArrayList<Comment> getCommentsByBlogId(int blogId);

  Double getAverageRating(int blogId);

  boolean hasUserRatedBlog(int blogId, String userEmail);
}