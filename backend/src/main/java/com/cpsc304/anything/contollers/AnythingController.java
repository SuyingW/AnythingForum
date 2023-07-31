package com.cpsc304.anything.contollers;

import com.cpsc304.anything.database.DatabaseConfig;
import com.cpsc304.anything.database.DatabaseConnectionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class AnythingController {
    private final ApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);

    @GetMapping("/")
    public Map<String, String> test() {
        DatabaseConnectionHandler handler = context.getBean(DatabaseConnectionHandler.class);
        return Collections.singletonMap("response", "your string value");
    }
}
