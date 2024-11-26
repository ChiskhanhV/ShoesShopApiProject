package com.example.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.api.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	Category getById(int id);
	
//	Page<Category> findAll(Pageable pageable);
	
	List<Category> findAll();
}