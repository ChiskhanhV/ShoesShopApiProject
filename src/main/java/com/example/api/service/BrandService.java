package com.example.api.service;

import java.util.List;

//import org.springframework.data.domain.Page;

import com.example.api.entity.Brand;

public interface BrandService {
	Brand saveBrand(Brand Brand);
	Brand getBrandById(int id);
	List<Brand> getAllBrand();
	void deleteBrandById(int id);
//	Page<Brand> getBrandByPagination(int page, int pageSize);
}
