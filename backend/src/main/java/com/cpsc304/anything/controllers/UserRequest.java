package com.cpsc304.anything.controllers;

public class UserRequest {
    public String email;
    public String password;

    UserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
