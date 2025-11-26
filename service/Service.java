package com.ourblogs.service;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.Blog;
import com.ourblogs.entity.Blogger;

public class Service {

  // Fields are now private final, not static
  private final AccountDao accountDao;
  private final BloggerDao bloggerDao;
  private final BlogDao blogDao;

  // Constructor uses instance fields
  public Service(AccountDao accountDao, BloggerDao bloggerDao, BlogDao blogDao) {
    this.accountDao = accountDao;
    this.bloggerDao = bloggerDao;
    this.blogDao = blogDao;
  }

  // Removed 'static'
  public Account register(Account acc, Blogger blo) {
    Account account1 = getAccountByEmail(acc.getEmail());

    if (account1 != null) {
      return null;
    }

    else {
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

    Blog bl = new Blog();
    int id;
    id = blogDao.getLastId();
    if(id == -1){
      blog.setBlogId(1);
    }
    else{
      blog.setBlogId(id + 1);
    }
    bl = blogDao.insertBlog(blog);
    if(bl == null) {
      return null;
    }
    else{
      return bl;
    }
  }

  // Removed 'static'
  public ArrayList<Blog> viewBlogsbyEmail(String email)
  {
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
    int flag=0;
    flag=bloggerDao.checkfriends(loginemail, bloggerEmailAddress);
    return flag;
  }
}