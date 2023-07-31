package com.cpsc304.anything.database;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    @Bean(destroyMethod = "close")
    public DatabaseConnectionHandler dbHandler() {
        DatabaseConnectionHandler handler = new DatabaseConnectionHandler();
        Dotenv dotenv = Dotenv.load();
        handler.login(dotenv.get("ORACLE_USERNAME"), dotenv.get("ORACLE_PASSWORD"));
        return handler;
    }
}
