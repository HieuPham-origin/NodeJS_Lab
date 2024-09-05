package com.example.demo;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Base64;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
//		var key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//		System.out.println("Generated Key: " + Base64.getEncoder().encodeToString(key.getEncoded()));

		SpringApplication.run(DemoApplication.class, args);
	}

}
