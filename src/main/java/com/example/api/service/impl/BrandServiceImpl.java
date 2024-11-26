package com.example.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.api.entity.Brand;
import com.example.api.repository.BrandRepository;
import com.example.api.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService{
	@Autowired
	BrandRepository BrandRepository;

	@Override
	public Brand saveBrand(Brand Brand) {
		// TODO Auto-generated method stub
		return BrandRepository.save(Brand);
	}

	@Override
	public Brand getBrandById(int id) {
		// TODO Auto-generated method stub
		return BrandRepository.getById(id);
	}

	@Override
	public List<Brand> getAllBrand() {
		 //TODO Auto-generated method stub
		return BrandRepository.findAll();
	}

	@Override
	public void deleteBrandById(int id) {
		BrandRepository.deleteById(id);
	}

}
