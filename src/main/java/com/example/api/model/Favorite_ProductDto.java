package com.example.api.model;

import java.util.Date;

import com.example.api.entity.Product;
import com.example.api.entity.User;

import lombok.Data;

@Data
public class Favorite_ProductDto {
	private int favoriteId;
	private Date addAt;
	private User user;
	private Product product;
}
