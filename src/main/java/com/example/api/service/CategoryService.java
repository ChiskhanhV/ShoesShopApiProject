package com.example.api.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.api.entity.Category;

public interface CategoryService {
	Category saveCategory(Category category);
	Category getCategoryById(int id);
	List<Category> getAllCategory();
	void deleteCategoryById(int id);
	Page<Category> getCategoryByPagination(int page, int pageSize);
}
