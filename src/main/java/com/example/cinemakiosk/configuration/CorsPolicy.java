package com.example.cinemakiosk.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@EnableWebMvc
public class CorsPolicy implements WebMvcConfigurer {

	@Value("${cross-origins}")
	String crossOrigins;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		log.info("Loading Cors configuration");
		log.info("Cross Origins setted to: {}", this.crossOrigins);
		registry.addMapping("/api/**")
		.allowedOrigins(crossOrigins)
		.allowedMethods("*")
		.allowedHeaders("*")
		.allowCredentials(true)
		.maxAge(3600);
	}
}



