package com.cpsc304.anything.contollers;

import com.cpsc304.anything.database.DatabaseConnectionHandler;
import com.cpsc304.anything.delegate.LoginWindowDelegate;
import com.cpsc304.anything.delegate.UserDelegate;
import com.cpsc304.anything.models.User;
import com.cpsc304.anything.ui.Forum;
import com.cpsc304.anything.ui.LoginWindow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class AnythingController implements LoginWindowDelegate, UserDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;

    public AnythingController() {
        this.dbHandler = new DatabaseConnectionHandler();
    }

    public void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
    }

//    @GetMapping("/")
//    public Map<String, String> test() {
//        return Collections.singletonMap("response", "your string value");
//    }


    @Override
    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();

            Forum forum = new Forum();
            forum.setupDatabase(this);
            forum.showMainMenu(this);
        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }

    @Override
    public void databaseSetup() {
        dbHandler.databaseSetup();
    }

    public void insertUser(User user) {
        dbHandler.insertUser(user);
    }

    @Override
    public void showUser() {
        User[] models = dbHandler.getUserInfo();

        for (int i = 0; i < models.length; i++) {
            User user = models[i];

            // simplified output formatting; truncation may occur
            System.out.printf("%-10.10s", user.getUserID());
            System.out.printf("%-10.10s", user.getUserName());
            System.out.printf("%-20.20s", user.getEmail());
            System.out.printf("%-20.20s", user.getUserPassword());
            System.out.println();
        }
    }

    public void forumFinished() {
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }

}
