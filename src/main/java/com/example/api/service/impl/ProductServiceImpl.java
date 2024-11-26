package com.example.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.api.entity.Product;
import com.example.api.repository.ProductRepository;
import com.example.api.service.ProductService;
import com.example.api.specification.ProductSpecification;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productRepository;

	@Override
	public List<Product> getAllProduct() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	public Product saveProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}

	@Override
	public Product getProductById(int id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id);
	}

	@Override
	public void deleteProductById(int id) {
		productRepository.deleteById(id);
	}

	@Override
	public List<Product> findTop12ProductBestSellers() {
		// TODO Auto-generated method stub
		return productRepository.findTop12ProductBestSellers();
	}

	@Override
	public List<Product> findTop12ProductNewArrivals() {
		// TODO Auto-generated method stub
		return productRepository.findTop12ProductNewArrivals();
	}

	@Override
	public Page<Product> findProductsWithPagination(int page, int pageSize) {
		Page<Product> products = productRepository.findAll(PageRequest.of(page, pageSize));
		return products;
	}

	@Override
	public List<Product> findProductIsOnSale() {
		// TODO Auto-generated method stub
		return productRepository.findProductIsOnSale();
	}

	@Override
	public Page<Product> findByProduct_NameContaining(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return productRepository.findByProductNameContaining(name, pageable);
	}

	@Override
	public Page<Product> filterProducts(String categoryName, Integer minPrice, Integer maxPrice, String sortOrder,
			int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Specification<Product> spec = Specification.where(null);

		if (categoryName != null && !categoryName.isEmpty()) {
			spec = spec.and(ProductSpecification.hasCategory(categoryName));
		}

		if (minPrice != null && maxPrice != null) {
			spec = spec.and(ProductSpecification.hasPriceBetween(minPrice, maxPrice));
		}

		spec = spec.and(ProductSpecification.orderByPrice(sortOrder));

		return productRepository.findAll(spec, pageable);
	}

	@Override
	public List<Product> getProductNotInPromotion() {
		// TODO Auto-generated method stub
		return productRepository.findByProductNotInPromotion();
	}

	@Override
	public List<Product> getProductByPromotionID(int id) {
		// TODO Auto-generated method stub
		return productRepository.findProductByPromotionID(id);
	}

}
