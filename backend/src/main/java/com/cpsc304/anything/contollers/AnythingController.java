package com.cpsc304.anything.contollers;

import com.cpsc304.anything.database.DatabaseConfig;
import com.cpsc304.anything.database.DatabaseConnectionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class AnythingController {
    @Autowired private DatabaseConnectionHandler handler;

    @GetMapping("/")
    public Map<String, String> test() {
        handler.getTableNames();
        return Collections.singletonMap("success", "true");
    }
}
