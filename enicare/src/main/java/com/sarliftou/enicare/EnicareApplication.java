package com.sarliftou.enicare;

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
//public int getNombreEmployeJPQL();
//• public List<String> getAllEmployeNamesJPQL();