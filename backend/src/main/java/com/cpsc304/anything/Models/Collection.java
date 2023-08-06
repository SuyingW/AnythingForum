package com.cpsc304.anything.Models;

public class Collection {

    public int collectionID;
    public String title;
    public int userID;
    public String alias;

    public Collection(int collectionID, String title, int userID, String alias) {
        this.userID = userID;
        this.collectionID = collectionID;
        this.title = title;
        this.alias = alias;
    }

    public int getUserID() {
        return userID;
    }

    public void setCollTitle(String title) {
        this.title = title;
    }
}
