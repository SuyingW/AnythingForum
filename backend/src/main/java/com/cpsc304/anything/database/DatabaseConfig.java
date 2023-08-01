package com.cpsc304.anything.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    @Bean(initMethod = "login", destroyMethod = "close")
    public DatabaseConnectionHandler handler() {
        return new DatabaseConnectionHandler();
    }
}
