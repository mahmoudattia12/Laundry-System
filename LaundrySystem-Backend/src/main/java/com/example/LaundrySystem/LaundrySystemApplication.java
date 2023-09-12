package com.example.LaundrySystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@SpringBootApplication
public class LaundrySystemApplication {

	public static void main(String[] args) {
		String [] arr = {"JoJoo@gmail.com", "Mahmoud272s22@gmail.com", "omar34@gmail.com", "admin@gmail.com"};
		Arrays.sort(arr);
		for(String st : arr){
			System.out.println(st);
		}
		SpringApplication.run(LaundrySystemApplication.class, args);
	}

}
