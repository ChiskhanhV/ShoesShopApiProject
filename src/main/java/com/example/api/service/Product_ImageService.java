package com.example.api.service;

import com.example.api.entity.Product_Image;

public interface Product_ImageService {
	Product_Image saveProduct_Image(Product_Image product_image);
	void deleteById(int id);
	void deleteProductImagesByProductId(int id);
}
