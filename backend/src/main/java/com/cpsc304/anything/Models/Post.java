package com.cpsc304.anything.Models;

import java.util.Date;

public class Post {
    public int postID;
    public String title;
    public String content;
    public Date publishDate;
    public int categoryID;
    public String categoryName;
    public int userID;
    public String alias;


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getWriterAlias() {
        return alias;
    }

    public void setWriterAlias(String alias) {
        this.alias = alias;
    }

    public Post(int postID, String title, String content, int categoryID, String categoryName, int userID, String alias) {
        this.postID = postID;
        this.title = title;
        this.content = content;
        this.categoryID = categoryID;
        this.userID = userID;
        this.publishDate = new Date();
        this.categoryName = categoryName;
        this.alias = alias;
    }


    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

}
