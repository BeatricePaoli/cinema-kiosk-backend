package com.example.cinemakiosk;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class CinemaKioskApplication {

	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
	}

	public static void main(String[] args) {
		SpringApplication.run(CinemaKioskApplication.class, args);
	}

}
