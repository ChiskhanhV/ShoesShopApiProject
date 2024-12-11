package com.example.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.entity.Category;
import com.example.api.service.CategoryService;
import com.example.api.service.CloudinaryService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@Autowired
	CloudinaryService cloudinaryService;

	@GetMapping
	public ResponseEntity<List<Category>> GetCategory() {
		List<Category> listCategories = categoryService.getAllCategory();
		if (listCategories != null) {
			return new ResponseEntity<>(listCategories, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(listCategories, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/pagination/{page}/{pageSize}")
	public ResponseEntity<Page<Category>> findProductsWithPagination(@PathVariable int page,@PathVariable int pageSize){
		Page<Category> categories = categoryService.getCategoryByPagination(page, pageSize);
		return new ResponseEntity<>(categories, HttpStatus.OK); 
	}

	@PostMapping(path = "/add")
	public ResponseEntity<Category> newCategory(@RequestParam String categoryName,
			@RequestParam Boolean status) {
		Category newCategory = new Category();
		newCategory.setCategory_Name(categoryName);
		newCategory.setStatus(status);

//		if (categoryImage != null) {
//			String url = cloudinaryService.uploadFile(categoryImage);
//			newCategory.setCategory_Image(url);
//		}

		newCategory = categoryService.saveCategory(newCategory);
		if (newCategory != null) {
			System.out.println("New category add success.");
			return new ResponseEntity<>(newCategory, HttpStatus.OK);
		} else {
			System.out.println("New category add failed.");
			return new ResponseEntity<>(newCategory, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/edit/{id}")
	public ResponseEntity<Category> editCategory(@PathVariable Integer id, 
			@RequestParam(required = false) String categoryName,
			@RequestParam(required = false) Boolean status) {

		Category editCategory = categoryService.getCategoryById(id);
		if (editCategory != null) {
//			if (categoryImage != null) {
//				String url = cloudinaryService.uploadFile(categoryImage);
//				editCategory.setCategory_Image(url);
//			}
			if (categoryName != null) {
				editCategory.setCategory_Name(categoryName);
			}
			editCategory.setStatus(status);
			editCategory = categoryService.saveCategory(editCategory);
			System.out.println("Edit category success.");
			return new ResponseEntity<Category>(editCategory, HttpStatus.OK);
		} else {
			System.out.println("Edit category failed.");
			return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        try {
            Category category = categoryService.getCategoryById(id);
            if (category != null) {
                categoryService.deleteCategoryById(id);
                System.out.println("Category with ID " + id + " has been deleted");
                return ResponseEntity.ok("Category with ID " + id + " has been deleted");
            } else {
                System.out.println("Category with ID " + id + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with ID " + id + " not found");
            }
        } catch (Exception e) {
            System.out.println("Error deleting category with ID " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting category with ID " + id);
        }
    }
}
