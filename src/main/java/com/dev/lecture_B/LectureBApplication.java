package com.dev.lecture_B;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LectureBApplication {

	public static void main(String[] args) {
		SpringApplication.run(LectureBApplication.class, args);
	}

}
