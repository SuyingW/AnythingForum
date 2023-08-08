package com.cpsc304.anything.controllers;

public class PostRequest {
    public int postID;
    public String title;
    public String content;

    PostRequest(int postID, String title, String content) {
        this.postID = postID;
        this.title = title;
        this.content = content;
    }
}
