package com.cpsc304.anything.model;

import oracle.sql.DATE;

public class User {

    private final int userID;
    private final DATE registrationDate;
    private final char userName;
    private final char email;
    private final char userPassword;



    public User(int userID, DATE registrationDate, char userName, char email, char userPassword) {
        this.userID = userID;
        this.registrationDate = registrationDate;
        this.userName = userName;
        this.email = email;
        this.userPassword = userPassword;
    }

    public int getUserID() {
        return userID;
    }

    public DATE getRegistrationDate() {
        return registrationDate;
    }

    public char getUserName() {
        return userName;
    }

    public char getEmail() {
        return email;
    }

    public char getUserPassword() {
        return userPassword;
    }
}
