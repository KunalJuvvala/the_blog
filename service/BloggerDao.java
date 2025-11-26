package com.ourblogs.service;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blogger;

public interface BloggerDao {
	ArrayList<Blogger> getAllBloggers() throws SQLException;
	Blogger getBloggerByEmail(String email);
	Blogger insertBlogger(Blogger blogger);
	void insertReader(Blogger blogger, String loginemail);
	int checkfriends(String loginemail, String bloggerEmailAddress);
}