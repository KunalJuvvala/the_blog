package com.ourblogs.entity;

public class BloggerDTO {
    
    private String email;
    private String password;
    private String bloggerName;
    private String isPublic;
    
    public String getBloggerName() {
        return bloggerName;
    }
    
    public String getisPublic() {
    	return isPublic;
    }
    
    public void setisPublic(String isPublic) {
    	this.isPublic = isPublic;
    }
    
    public void setBloggerName(String bloggerName) {
        this.bloggerName = bloggerName;
    }
    
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
