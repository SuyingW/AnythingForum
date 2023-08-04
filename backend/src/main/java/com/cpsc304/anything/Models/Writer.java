package com.cpsc304.anything.Models;


public class Writer {

    public int userID;
    public String alias;

    public Writer(int userID) {
        this.userID = userID;
        this.alias = null;
    }

    public int getUserID() {
        return userID;
    }

    public void setWriterAlias(String alias) {
        this.alias = alias;

    }
}