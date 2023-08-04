package com.cpsc304.anything.controllers;

public class WriterRequest {
    public int userID;
    public String alias;

    WriterRequest(int userID, String alias) {
        this.userID = userID;
        this.alias = alias;
    }
}
