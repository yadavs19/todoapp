package com.ibm.todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ibm.todoapp.serviceImpl.SpringSecurityAuditorAware;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@EnableJpaAuditing(auditorAwareRef="auditorAware")
@SpringBootApplication
@SecurityScheme(name = "TodoApp", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class TodoappApplication {

	@Bean
	public AuditorAware<String> auditorAware() {
		return new SpringSecurityAuditorAware();
	}

	public static void main(String[] args) {
		SpringApplication.run(TodoappApplication.class, args);
	}

}
