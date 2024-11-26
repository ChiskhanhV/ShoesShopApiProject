package com.example.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@CrossOrigin("*")
@RestController
public class ProvinceController {
	@GetMapping("/api/v1/provinces")
	public ResponseEntity<Object> getProvinces() {
		String url = "https://esgoo.net/api-tinhthanh/1/0.htm";
		RestTemplate restTemplate = new RestTemplate();
		try {
			Object provinces = restTemplate.getForObject(url, Object.class);
			return new ResponseEntity<>(provinces, HttpStatus.OK);
		} catch (HttpServerErrorException e) {
			return new ResponseEntity<>("Service temporarily unavailable. Please try again later.",
					HttpStatus.SERVICE_UNAVAILABLE);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred while fetching data.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/api/v1/districts/{province_id}")
	public ResponseEntity<Object> getDistricts(@PathVariable String province_id) {
		String url = "https://esgoo.net/api-tinhthanh/2/" + province_id + ".htm";
		RestTemplate restTemplate = new RestTemplate();
		try {
			Object districts = restTemplate.getForObject(url, Object.class);
			return new ResponseEntity<>(districts, HttpStatus.OK);
		} catch (HttpServerErrorException e) {
			return new ResponseEntity<>("Service temporarily unavailable. Please try again later.",
					HttpStatus.SERVICE_UNAVAILABLE);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred while fetching data.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/api/v1/wards/{district_id}")
	public ResponseEntity<Object> getWards(@PathVariable String district_id) {
		String url = "https://esgoo.net/api-tinhthanh/3/" + district_id + ".htm";
		RestTemplate restTemplate = new RestTemplate();
		try {
			Object wards = restTemplate.getForObject(url, Object.class);
			return new ResponseEntity<>(wards, HttpStatus.OK);
		} catch (HttpServerErrorException e) {
			return new ResponseEntity<>("Service temporarily unavailable. Please try again later.",
					HttpStatus.SERVICE_UNAVAILABLE);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred while fetching data.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
