package com.taskflow.vacation.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@org.springframework.data.jpa.repository.config.EnableJpaAuditing
@org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
