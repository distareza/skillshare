package com.mytutorial.springbootscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootschedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootschedulerApplication.class, args);
	}

}
