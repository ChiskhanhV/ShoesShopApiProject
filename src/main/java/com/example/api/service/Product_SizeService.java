package com.example.api.service;

import com.example.api.entity.Product_Size;

public interface Product_SizeService {
	Product_Size saveProduct_Size(Product_Size product_size);
	void deleteById(int id);
	void deleteProductSizesByProductId(int id);
}
