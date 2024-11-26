package com.example.api.controller;

import java.util.ArrayList;
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

import com.example.api.entity.Cart;
import com.example.api.entity.Product;
import com.example.api.entity.User;
import com.example.api.model.CartDto;
import com.example.api.service.CartService;
import com.example.api.service.ProductService;
import com.example.api.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/cart")
public class CartController {
	@Autowired
	CartService cartService;

	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;
	
	@Autowired
	ModelMapper modelMapper;

	@PostMapping(path = "/addtocart")
	public ResponseEntity<List<Cart>> addToCart(String user_id, int product_id, int count, int size) {
		System.out.println(user_id + product_id + count + size);
		User user = userService.findByIdAndRole(user_id, "user");
		List<Cart> listCart = cartService.GetAllCartByUser_id(user_id);
		Product product = productService.getProductById(product_id);
		int flag = 0;
		for (Cart y : listCart) {
			if (y.getProduct().getId() == product_id && y.getSize() == size) {
				y.setCount(y.getCount() + count);
				cartService.saveCart(y);
				flag = 1;
			}
		}
		if (flag == 0) {
			Cart newCart = new Cart();
			newCart.setCount(count);
			newCart.setSize(size);
			newCart.setProduct(product);
			newCart.setUser(user);
			cartService.saveCart(newCart);
		}
		listCart = cartService.GetAllCartByUser_id(user_id);
		return new ResponseEntity<>(listCart, HttpStatus.OK);
	}

	@GetMapping(path = "/cartofuser")
	public ResponseEntity<List<CartDto>> cartOfUser(String user_id) {
		List<Cart> listCart = cartService.GetAllCartByUser_id(user_id);
		List<CartDto> lisCartDto = new ArrayList<>();
		for (Cart y : listCart) {
			CartDto cartDto = modelMapper.map(y, CartDto.class);
			System.out.println(cartDto);
			lisCartDto.add(cartDto);
		}
		return new ResponseEntity<>(lisCartDto, HttpStatus.OK);
	}

	@DeleteMapping(path = "/deletetocart")
	public ResponseEntity<String> DeleteCart(@RequestParam int cart_id, @RequestParam String user_id) {
		List<Cart> carts = cartService.GetAllCartByUser_id(user_id);
		System.out.println(cart_id + user_id);
		for (Cart y : carts) {
			if (cart_id == y.getId())
				cartService.deleteById(cart_id);
		}
		return new ResponseEntity<>("successfully", HttpStatus.OK);
	}
}
