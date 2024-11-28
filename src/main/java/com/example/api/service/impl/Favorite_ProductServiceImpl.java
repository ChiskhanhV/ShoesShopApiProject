package com.example.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.entity.Favorite_Product;
import com.example.api.repository.Favorite_ProductRepository;
import com.example.api.service.Favorite_ProductService;

@Service
public class Favorite_ProductServiceImpl implements Favorite_ProductService {
	@Autowired
	Favorite_ProductRepository FavoriteProductRepository;
	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		FavoriteProductRepository.deleteById(id);
	}

	@Override
	public List<Favorite_Product> GetAllFavoriteByUser_id(String user_id) {
		// TODO Auto-generated method stub
		return FavoriteProductRepository.findAllByUser_id(user_id);
	}

	@Override
	public Favorite_Product saveFavorite(Favorite_Product Favorite) {
		// TODO Auto-generated method stub
		return FavoriteProductRepository.save(Favorite);
	}
	
}
