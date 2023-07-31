package com.cpsc304.anything.delegate;

import com.cpsc304.anything.models.User;

public interface UserDelegate {
    public void databaseSetup();
    public void insertUser(User user);
    public void showUser();
}
