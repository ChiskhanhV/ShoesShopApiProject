package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.api.entity.Product_Size;

import jakarta.transaction.Transactional;

public interface Product_SizeRepository extends JpaRepository<Product_Size, Integer> {
	void deleteById(int id);

	@Modifying
	@Transactional
	@Query("DELETE FROM Product_Size pz WHERE pz.product.id =?1")
	void deleteProductSizesByProductId(int productId);

}
