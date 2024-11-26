package com.example.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
@EnableWebMvc
public class OpenAPIConfig implements WebMvcConfigurer {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()				
				.info(new Info().title("Shoes Shop Api Project").description(
						"The Shoes Shop API provides endpoints for product, order, and customer management. This API allows performing CRUD (Create, Read, Update, Delete) operations on main objects in the shoe store system.")
						.version("1.0"));			
	}
}