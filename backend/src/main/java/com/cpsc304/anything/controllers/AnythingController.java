package com.cpsc304.anything.controllers;

import com.cpsc304.anything.Models.User;
import com.cpsc304.anything.database.DatabaseConnectionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
public class AnythingController {
    @Autowired private DatabaseConnectionHandler handler;

    @GetMapping("/")
    public Map<String, String> test() {
        handler.getTableNames();
        return Collections.singletonMap("success", "true");
    }

    @PostMapping("/login")
    public Map<String, String> userLogin(@RequestBody UserRequest user) {
        return Collections.singletonMap("username", handler.userLogin(user.email, user.password));
    }

    @PostMapping("/registration")
    public Map<String, String> userRegistration(@RequestBody User user) {
        return Collections.singletonMap("username", handler.userRegistration(user.userID, user.email, user.userName, user.userPassword));
    }

    @GetMapping("/users")
    public Map<String, User[]> userList(@RequestBody User user) {
        return Collections.singletonMap("success", handler.userList());
    }
}
