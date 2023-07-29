package com.cpsc304.anything;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cpsc304.anything.database.DatabaseConnectionHandler;

@SpringBootApplication
public class AnythingApplication {
    private DatabaseConnectionHandler dbHandler = null;

	public static void main(String[] args) {
		SpringApplication.run(AnythingApplication.class, args);
	}

}
