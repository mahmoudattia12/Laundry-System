package com.example.LaundrySystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class LaundrySystemApplication {
	public static void main(String[] args) {
//		LocalDateTime endDate = LocalDateTime.parse("2023-09-24T04:48:01");
//		System.out.println(endDate);
		SpringApplication.run(LaundrySystemApplication.class, args);
	}
}
