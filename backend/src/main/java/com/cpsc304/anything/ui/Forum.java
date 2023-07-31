package com.cpsc304.anything.ui;

import com.cpsc304.anything.contollers.AnythingController;
import com.cpsc304.anything.delegate.UserDelegate;
import com.cpsc304.anything.models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class Forum {

    private BufferedReader bufferedReader = null;
    private UserDelegate delegate = null;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";
    private static final int EMPTY_INPUT = 0;
    private static final int INVALID_INPUT = Integer.MIN_VALUE;

    private AnythingController controller = null;

    public Forum() {

    }

    public void setupDatabase(UserDelegate delegate) {
        this.delegate = delegate;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 1) {
            System.out.println("press 1 to setup database");
            choice = readInteger(false);

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        delegate.databaseSetup();
                        break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.\n");
                        break;
                }
            }
        }

    }



    public void showMainMenu(AnythingController controller) {
        this.controller = controller;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int choice = INVALID_INPUT;

        while (choice != 5) {
            System.out.println();
            System.out.println("1. Insert User");
            System.out.println("4. Show User");

            choice = readInteger(false);

            System.out.println(" ");

            if (choice != INVALID_INPUT) {
                switch (choice) {
                    case 1:
                        handleInsertOption();
                        break;
                    case 4:
                        delegate.showUser();
                        break;
                    default:
                        System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
                        break;
                }
            }
        }
    }

    private void handleInsertOption() {
        String userName = null;
        while ( userName == null || userName.length() <= 0) {
            System.out.print("Please enter the user name: ");
            userName = readLine().trim();
        }

        String email = null;
        while (email == null || email.length() <= 0) {
            System.out.print("Please enter email: ");
            email = readLine().trim();
        }


        String password = null;
        while (password == null || password.length() <= 0) {
            System.out.print("Please enter password: ");
            password = readLine().trim();
        }




        User user = new User(userName, email, password);
        controller.insertUser(user);
    }

        private int readInteger(boolean allowEmpty) {
            String line = null;
            int input = INVALID_INPUT;
            try {
                line = bufferedReader.readLine();
                input = Integer.parseInt(line);
            } catch (IOException e) {
                System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            } catch (NumberFormatException e) {
                if (allowEmpty && line.length() == 0) {
                    input = EMPTY_INPUT;
                } else {
                    System.out.println(WARNING_TAG + " Your input was not an integer");
                }
            }
            return input;
        }

    private String readLine() {
        String result = null;
        try {
            result = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result;
    }

}