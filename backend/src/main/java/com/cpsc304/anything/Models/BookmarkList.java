package com.cpsc304.anything.Models;

public class BookmarkList {
    public int userID;
    public int listID;
    public String listName;

    public BookmarkList(int userID, int listID, String listName) {
        this.userID = userID;
        this.listID = listID;
        this.listName = listName;
    }
}
