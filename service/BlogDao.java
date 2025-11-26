package com.ourblogs.service;
import java.util.ArrayList;
import com.ourblogs.entity.Blog;


public interface BlogDao {
	ArrayList<Blog> getBlogsByEmail(String email);
	Blog insertBlog(Blog blog);
	int getLastId();
}  