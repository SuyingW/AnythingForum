package com.cpsc304.anything.Models;

import java.util.Date;

public class Post {
    public Integer postID;
    public String title;
    public String content;
    public Date publishDate;
    public Integer categoryID;
    public String categoryName;
    public Integer userID;
    public String alias;

    public Post(Integer postID, String title, String content, Integer categoryID, String categoryName, Integer userID, String alias, Date publishDate) {
        this.postID = postID;
        this.title = title;
        this.content = content;
        this.categoryID = categoryID;
        this.userID = userID;
        this.publishDate = publishDate;
        this.categoryName = categoryName;
        this.alias = alias;
    }

}
