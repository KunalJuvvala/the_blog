/*
package com.ourblogs.ui.view;

import java.util.ArrayList;

import com.ourblogs.entity.Blog;

public class insertedBlogview {
   public static String InstertedBlogView(String message, ArrayList<Blog> viewBlog) {
   	 		   
      String msg1 = null;
      if (viewBlog !=null)
      {
         for (Blog blog : viewBlog) {
            msg1 =  "\nId: " + blog.getBlogId()+ "\nSubject: " + blog.getBlogSubject()+"\nBody: " 
                     + blog.getBlogBody() + "\nDate: " + blog.getBlogDate() + "\n--------------------";
            message += msg1;
         }
            
      }
      else if(viewBlog == null) {
         msg1="\n No Blog Found";
         message+=msg1;
      }
      return message;
   	 
   }
	 
   public static void dispmsg( String message)
   {
      System.out.println(message);
   }
}
*/
