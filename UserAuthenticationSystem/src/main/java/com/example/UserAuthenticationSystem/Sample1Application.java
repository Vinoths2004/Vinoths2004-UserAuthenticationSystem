package com.example.UserAuthenticationSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.UserAuthenticationSystem.Sample1Application;

@SpringBootApplication
@EnableScheduling
public class Sample1Application {

	public static void main(String[] args) {
		SpringApplication.run(Sample1Application.class, args);
		
	}
	
}
