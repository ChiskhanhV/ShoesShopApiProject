package com.example.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entity.Favorite_Product;
import com.example.api.entity.Product;
import com.example.api.entity.User;
import com.example.api.model.Favorite_ProductDto;
import com.example.api.service.Favorite_ProductService;
import com.example.api.service.ProductService;
import com.example.api.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/favorite")
public class Favorite_ProductController {
	@Autowired
	Favorite_ProductService favoriteService;

	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@PostMapping(path = "/addtofavorite")
	public ResponseEntity<Favorite_Product> addToFavorite(String user_id, int product_id) {
		User user = userService.findByIdAndRole(user_id, "user");
		Product product = productService.getProductById(product_id);
		Favorite_Product newFavorite = new Favorite_Product();
		newFavorite.setUser(user);
		newFavorite.setProduct(product);
		long millis = System.currentTimeMillis();
		java.sql.Date addAt = new java.sql.Date(millis);
		newFavorite.setAddedAt(addAt);
		favoriteService.saveFavorite(newFavorite);
		return new ResponseEntity<>(newFavorite, HttpStatus.OK);
	}
	
	@GetMapping(path = "/favoriteofuser")
	public ResponseEntity<List<Favorite_ProductDto>> Favorite_ProductOfUser(String user_id) {
		List<Favorite_Product> listFavorite_Product = favoriteService.GetAllFavoriteByUser_id(user_id);
		List<Favorite_ProductDto> lisFavorite_ProductDto = new ArrayList<>();
		for (Favorite_Product y : listFavorite_Product) {
			Favorite_ProductDto Favorite_ProductDto = modelMapper.map(y, Favorite_ProductDto.class);
			System.out.println(Favorite_ProductDto);
			lisFavorite_ProductDto.add(Favorite_ProductDto);
		}
		return new ResponseEntity<>(lisFavorite_ProductDto, HttpStatus.OK);
	}

	@DeleteMapping(path = "/deletetoFavorite")
	public ResponseEntity<String> DeleteFavorite_Product(@RequestParam int Favorite_Product_id, @RequestParam String user_id) {
		List<Favorite_Product> Favorite_Products = favoriteService.GetAllFavoriteByUser_id(user_id);
		System.out.println(Favorite_Product_id + user_id);
		for (Favorite_Product y : Favorite_Products) {
			if (Favorite_Product_id == y.getFavoriteId())
				favoriteService.deleteById(Favorite_Product_id);
		}
		return new ResponseEntity<>("successfully", HttpStatus.OK);
	}
}
