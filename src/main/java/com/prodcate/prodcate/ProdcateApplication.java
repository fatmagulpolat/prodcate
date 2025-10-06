package com.prodcate.prodcate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "AuditorAwareImpl")
@SpringBootApplication
public class ProdcateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdcateApplication.class, args);
	}

}
