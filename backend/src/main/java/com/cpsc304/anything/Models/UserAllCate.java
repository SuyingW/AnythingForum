package com.cpsc304.anything.Models;

public class UserAllCate {
    public int userID;
    public String alias;
    public String userName;
    public String email;

    public UserAllCate(int userID, String alias, String userName, String email) {
        this.userID = userID;
        this.alias = alias;
        this.userName = userName;
        this.email = email;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
