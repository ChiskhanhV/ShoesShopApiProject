package com.example.api.service;

import java.util.List;

import com.example.api.entity.Favorite_Product;

public interface Favorite_ProductService {
	void deleteById(int id);

	List<Favorite_Product> GetAllFavoriteByUser_id(String user_id);

	Favorite_Product saveFavorite(Favorite_Product Favorite);
}
