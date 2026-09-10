package com.example.workorder_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkorderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkorderServiceApplication.class, args);
		System.out.println("application started");
	}

}
