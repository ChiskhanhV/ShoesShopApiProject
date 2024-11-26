package com.example.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.api.entity.Product;

public interface ProductService {
	List<Product> getAllProduct();

	Product saveProduct(Product product);

	Product getProductById(int id);

	void deleteProductById(int id);

	List<Product> findTop12ProductBestSellers();

	List<Product> findTop12ProductNewArrivals();

	Page<Product> findProductsWithPagination(int page, int pageSize);

	List<Product> findProductIsOnSale();

	Page<Product> findByProduct_NameContaining(String name, Pageable pageable);

	Page<Product> filterProducts(String categoryName, Integer minPrice, Integer maxPrice, String sortOrder, int page,
			int pageSize);
	
	List<Product> getProductNotInPromotion();
	
	List<Product> getProductByPromotionID(int id);
}
