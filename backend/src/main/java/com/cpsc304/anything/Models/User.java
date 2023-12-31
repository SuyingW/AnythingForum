package com.cpsc304.anything.Models;

import java.util.Date;

public class User {
    //private static int userCount = 0;
    public String userName;
    public String email;
    public String userPassword;
    public Date registrationDate;

    public int userID;

    public String alias;

    public User(int userID, String userName, String email, String userPassword, String alias) {
        this.userName = userName;
        this.email = email;
        this.userPassword = userPassword;
        this.registrationDate = new Date();
        this.userID = userID;
        this.alias = alias;
    }

    public int getUserID() { return userID; }

    public void setUserID(int userID) { this.userID = userID; }

    public Date getRegistrationDate() { return registrationDate; }

    public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

}
