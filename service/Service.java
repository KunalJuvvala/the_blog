package com.ourblogs.service;

import com.ourblogs.db.NotificationDaoImpl;
import com.ourblogs.db.TagDaoImpl;
import com.ourblogs.db.UserActivityDaoImpl;
import com.ourblogs.entity.Comment;
import com.ourblogs.entity.Notifications;
import com.ourblogs.entity.Rating;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ourblogs.service.InteractionDao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blog;
import com.ourblogs.entity.Blogger;

public class Service {

  // Fields are now private final, not static
  private final AccountDao accountDao;
  private final BloggerDao bloggerDao;
  private final BlogDao blogDao;
  private final InteractionDao interactionDao;
  private final UserActivityDaoImpl activityDao;
  private final NotificationDaoImpl notificationDao;
  private final TagDaoImpl tagDao;

  // Constructor uses instance fields
  public Service(AccountDao accountDao, BloggerDao bloggerDao, BlogDao blogDao, InteractionDao interactionDao,
                 UserActivityDaoImpl activityDao, NotificationDaoImpl notificationDao,
                 TagDaoImpl tagDao) {
    this.accountDao = accountDao;
    this.bloggerDao = bloggerDao;
    this.blogDao = blogDao;
    this.interactionDao = interactionDao;
    this.activityDao = activityDao;
    this.notificationDao = notificationDao;
    this.tagDao = tagDao;
  }

  // Removed 'static'
  public Account register(Account acc, Blogger blo) {
    Account account1 = getAccountByEmail(acc.getEmail());

    if (account1 != null) {
      return null;
    } else {
      Account account = new Account();
      account.setEmail(acc.getEmail());
      account.setPassword(acc.getPassword());

      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      String encodedPswd = encoder.encode(account.getPassword());
      account.setPassword(encodedPswd);
      acc.setPassword(encodedPswd);

      accountDao.insertAccount(account);
      Blogger blogger = new Blogger();
      blogger.setBloggerEmailAddress(blo.getBloggerEmailAddress());
      blogger.setBloggerName(blo.getBloggerName());
      blogger.setisPublic(blo.getisPublic());
      bloggerDao.insertBlogger(blogger);

      return acc;
    }
  }


  // Removed 'static'
  public Account getAccountByEmail(String email) {
    return accountDao.getAccountByEmail(email);
  }

  // Removed 'static'
  public Blogger getBloggerByEmail(String email) {
    return bloggerDao.getBloggerByEmail(email);
  }

  // Removed 'static'
  public ArrayList<Blogger> getallBloggers() throws SQLException {
    return bloggerDao.getAllBloggers();
  }

  public boolean authenticate(Account login) {
    Account account = accountDao.getAccountByEmail(login.getEmail());

    if (account != null) {
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      if (encoder.matches(login.getPassword(), account.getPassword())) {
        return true;
      }
    }

    return false;
  }

  public Blog insertBlog(Blog blog) {
    Blog bl = blogDao.insertBlog(blog);
    return bl;
  }

  // Removed 'static'
  public ArrayList<Blog> viewBlogsbyEmail(String email) {
    ArrayList<Blog> blogs = new ArrayList<>();
    ArrayList<Blog> bloggerBlogs = blogDao.getBlogsByEmail(email);
    blogs.addAll(bloggerBlogs);
    return blogs;

  }

  // Removed 'static'
  public void addFriend(Blogger reader, String loginemail) {
    // NOTE: Logic here implies loginemail is the reader and reader.getEmail is the blogger
    bloggerDao.insertReader(reader, loginemail);

  }

  // Removed 'static'
  public int isFriends(String loginemail, String bloggerEmailAddress) {
    int flag = 0;
    flag = bloggerDao.checkfriends(loginemail, bloggerEmailAddress);
    return flag;
  }

  public boolean updateBlog(Blog blog) {
    return blogDao.updateBlog(blog);
  }

  public boolean deleteBlog(int id) {
    return blogDao.deleteBlog(id);
  }


  public void addRating(Rating rating) {
    if (!interactionDao.hasUserRatedBlog(rating.getBlogId(), rating.getUserEmail())) {
      interactionDao.addRating(rating);
    }
  }

  public ArrayList<Comment> getCommentsByBlogId(int blogId) {
    return interactionDao.getCommentsByBlogId(blogId);
  }

  public Double getAverageRating(int blogId) {
    return interactionDao.getAverageRating(blogId);
  }

  public boolean hasUserRatedBlog(int blogId, String userEmail) {
    return interactionDao.hasUserRatedBlog(blogId, userEmail);
  }

  public void addComment(Comment c) {
    interactionDao.addComment(c);

    // Auto-Log Activity
    activityDao.logActivity(c.getCommenterEmail(), "COMMENT", "Blog ID: " + c.getBlogId());

    // Auto-Notify the Blog Owner (You need to find who owns the blog first to do this accurately)
    // For now, simple logging is sufficient
  }

  public ArrayList<Notifications> getNotifications(String email) {
    // This calls the DAO method we created earlier
    return notificationDao.getUnreadNotifications(email);
  }

  // 4. NEW METHOD FOR TAGS
  public void addTagToBlog(int blogId, String tagName, String email) {
    int tagId = tagDao.getOrCreateTagId(tagName);
    if(tagId != -1) {
      tagDao.addTagToBlog(blogId, tagId, email);
    }
  }

}