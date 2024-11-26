package com.example.api;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cloudinary.Cloudinary;

@SpringBootApplication
public class ShoesShopApiProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoesShopApiProjectApplication.class, args);
		System.out.println("-----------------------------------------------------------");
		System.out.println("🚀 Server ready at http://localhost:8090");
		System.out.println("🚀 Api doc ready at http://localhost:8090/swagger-ui.html ");
	}

	@Bean
	public Cloudinary getCloudinary() {
		Map<String, String> config = new HashMap<String, String>();
		config.put("cloud_name", "dedqzbz5c");
		config.put("api_key", "537813892717763");
		config.put("api_secret", "xxzbUE9_pjCHlvRgg01YQJUv-x0");
		return new Cloudinary(config);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}
}
