package com.cpsc304.anything.Models;

public class SpamResult {
    public int userID;
    public int numComments;

    public SpamResult(int userID, int numComments) {
        this.userID = userID;
        this.numComments = numComments;
    }
}
