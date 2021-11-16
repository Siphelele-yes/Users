package com;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin()
@SpringBootApplication
public class CrudApplicationUsingSpringBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(CrudApplicationUsingSpringBootApplication.class, args);
	}

}
