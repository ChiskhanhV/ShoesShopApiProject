package com.example.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.entity.Product_Image;
import com.example.api.repository.Product_ImageRepository;
import com.example.api.service.Product_ImageService;

@Service
public class Product_ImageServiceImpl implements Product_ImageService{
	@Autowired
	Product_ImageRepository product_ImageRepository;
	
	@Override
	public Product_Image saveProduct_Image(Product_Image product_image) {
		// TODO Auto-generated method stub
		return product_ImageRepository.save(product_image);
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		product_ImageRepository.deleteById(id);
	}

	@Override
	public void deleteProductImagesByProductId(int id) {
		product_ImageRepository.deleteProductsImageByProductId(id);
		
	}

}
