package com.example.api.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.api.entity.Category;
import com.example.api.repository.CategoryRepository;
import com.example.api.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Category saveCategory(Category category) {
		// TODO Auto-generated method stub
		return categoryRepository.save(category);
	}

	@Override
	public Category getCategoryById(int id) {
		// TODO Auto-generated method stub
		return categoryRepository.getById(id);
	}

	@Override
	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
//		return categoryRepository.findAll();
		List<Category> allCategories = categoryRepository.findAll();
	    Map<Integer, Category> categoryMap = new HashMap<>();
	    List<Category> rootCategories = new ArrayList<>();

	    // Đưa tất cả danh mục vào map
	    for (Category category : allCategories) {
	        categoryMap.put(category.getId(), category);
	    }

	    // Tạo cấu trúc cây
	    for (Category category : allCategories) {
	        if (category.getParentCategory() == null) {
	            rootCategories.add(category);
	        } else {
	            Category parent = categoryMap.get(category.getParentCategory().getId());
	            if (parent != null) {
	                parent.getSubCategories().add(category);
	            }
	        }
	    }
	    return rootCategories;
	}

	@Override
	public void deleteCategoryById(int id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public Page<Category> getCategoryByPagination(int page, int pageSize) {
		Page<Category> categories = categoryRepository.findAll(PageRequest.of(page, pageSize));
		return categories;
	}
	
}
