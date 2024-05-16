package com.example.RegistrationSystem;

import com.example.RegistrationSystem.service.NodeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RegistrationSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(RegistrationSystemApplication.class, args);
		NodeService.getInstance().buildDatabaseSchema();
	}

}
