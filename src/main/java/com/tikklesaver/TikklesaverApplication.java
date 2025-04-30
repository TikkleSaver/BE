package com.tikklesaver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TikklesaverApplication {

	public static void main(String[] args) {
		SpringApplication.run(TikklesaverApplication.class, args);
	}

}
