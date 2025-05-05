package com.sarliftou.enicare;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EnicareApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnicareApplication.class, args);
	}

}

