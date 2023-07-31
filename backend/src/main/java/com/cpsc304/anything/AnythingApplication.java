package com.cpsc304.anything;

import com.cpsc304.anything.contollers.AnythingController;
import com.cpsc304.anything.ui.Forum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cpsc304.anything.database.DatabaseConnectionHandler;

@SpringBootApplication
public class AnythingApplication {
    private DatabaseConnectionHandler dbHandler = null;

	public static void main(String[] args) {

		//SpringApplication.run(AnythingApplication.class, args);
		AnythingController anythingController = new AnythingController();
		anythingController.start();
	}

}
