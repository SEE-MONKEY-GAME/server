package com.seemonkey.bananajump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BananajumpApplication {

	public static void main(String[] args) {
		SpringApplication.run(BananajumpApplication.class, args);
	}

}
