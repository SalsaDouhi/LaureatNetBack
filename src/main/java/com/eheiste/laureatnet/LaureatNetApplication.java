package com.eheiste.laureatnet;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.eheiste.laureatnet.repository.PostRepository;

@EnableAsync
@SpringBootApplication
public class LaureatNetApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaureatNetApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200/") // Allow requests from any origin
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
						.allowedHeaders("Authorization", "Content-Type", "Accept", "x-requested-with", "Cache-Control") // Allow specific headers
						.maxAge(3600); // Max age of the CORS preflight request in seconds
			}
		};
	}
}
