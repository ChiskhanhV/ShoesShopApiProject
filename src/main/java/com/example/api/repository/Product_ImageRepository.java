package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.api.entity.Product_Image;

import jakarta.transaction.Transactional;

public interface Product_ImageRepository extends JpaRepository<Product_Image, Integer>{
	void deleteById(int id);

	@Modifying
	@Transactional
	@Query("DELETE FROM Product_Image pi WHERE pi.product.id =?1")
	void deleteProductsImageByProductId(int productId);
}
