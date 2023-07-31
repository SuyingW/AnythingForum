package com.cpsc304.anything.models;

import java.util.Date;
import java.util.UUID;

public class User {
    private static int userCount = 0;
    public String userName;
    public String email;
    public String userPassword;
    //public Date registrationDate;
    public String userID;

    public User(String userName, String email, String userPassword) {
        this.userName = userName;
        this.email = email;
        this.userPassword = userPassword;
        //this.registrationDate = new Date();
        this.userID = generateUserID();
    }

    private String generateUserID() {
        userCount++;
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID + "-" + userCount;
    }

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID; }

    //public Date getRegistrationDate() { return registrationDate; }

    //public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }

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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
