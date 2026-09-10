package com.example.workorder_scheduler_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WorkorderSchedulerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkorderSchedulerServiceApplication.class, args);
		System.out.println("application is running ");
	}

}
