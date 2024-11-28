package com.example.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entity.Favorite_Product;

public interface Favorite_ProductRepository extends JpaRepository<Favorite_Product, Integer> {
	List<Favorite_Product> findAllByUser_id(String user_id);
	
	void deleteById(int id);
}
