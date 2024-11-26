package com.example.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer>{
	Brand getById(int id);
	
//	Page<Category> findAll(Pageable pageable);
	
	List<Brand> findAll();
}
