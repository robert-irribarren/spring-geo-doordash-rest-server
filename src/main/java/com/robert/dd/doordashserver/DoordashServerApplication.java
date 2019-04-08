package com.robert.dd.doordashserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;




@EnableJpaAuditing
@SpringBootApplication
public class DoordashServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoordashServerApplication.class, args);
	}

}
