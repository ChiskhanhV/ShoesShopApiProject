package com.example.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.entity.Product_Size;
import com.example.api.repository.Product_SizeRepository;
import com.example.api.service.Product_SizeService;

@Service
public class Product_SizeServiceImpl implements Product_SizeService{
	@Autowired
	Product_SizeRepository product_SizeRepository;
	
	@Override
	public Product_Size saveProduct_Size(Product_Size product_size) {
		// TODO Auto-generated method stub
		return product_SizeRepository.save(product_size);
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		product_SizeRepository.deleteById(id);
	}

	@Override
	public void deleteProductSizesByProductId(int id) {
		// TODO Auto-generated method stub
		product_SizeRepository.deleteProductSizesByProductId(id);
	}

}
