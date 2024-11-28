package com.example.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
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

import com.example.api.entity.Brand;
import com.example.api.error.ErrorCode;
import com.example.api.error.ErrorResponse;
import com.example.api.service.BrandService;
import com.example.api.service.CloudinaryService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/brands")
public class BrandController {
	@Autowired
	BrandService BrandService;

	@Autowired
	CloudinaryService cloudinaryService;

	@GetMapping
	public ResponseEntity<?> GetBrand() {
	    try {
	        List<Brand> listBrands = BrandService.getAllBrand();
	        
	        if (listBrands.isEmpty()) {
	            return new ResponseEntity<>(
	                new ErrorResponse(ErrorCode.BRAND_NOT_FOUND.getCode(), ErrorCode.BRAND_NOT_FOUND.getMessage()),
	                HttpStatus.NOT_FOUND
	            );
	        }

	        return new ResponseEntity<>(listBrands, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(
	            new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMessage()),
	            HttpStatus.INTERNAL_SERVER_ERROR
	        );
	    }
	}

	
//	@GetMapping("/pagination/{page}/{pageSize}")
//	public ResponseEntity<Page<Brand>> findProductsWithPagination(@PathVariable int page,@PathVariable int pageSize){
//		Page<Brand> categories = BrandService.getBrandByPagination(page, pageSize);
//		return new ResponseEntity<>(categories, HttpStatus.OK); 
//	}

	@PostMapping(path = "/add", consumes = "multipart/form-data")
	public ResponseEntity<?> newBrand(@RequestParam String BrandName,
			@RequestParam MultipartFile BrandImage, @RequestParam Boolean status) {
		Brand newBrand = new Brand();
		newBrand.setBrand_name(BrandName);

		if (BrandImage != null) {
			String url = cloudinaryService.uploadFile(BrandImage);
			newBrand.setBrand_image(url);
		}
		newBrand.setStatus(status);
		newBrand = BrandService.saveBrand(newBrand);
		if (newBrand != null) {
			System.out.println("New Brand add success.");
			return new ResponseEntity<>(newBrand, HttpStatus.OK);
		} else {
			System.out.println("New Brand add failed.");
			return new ResponseEntity<>(new ErrorResponse(ErrorCode.BRAND_CREATION_FAILED.getCode(), ErrorCode.BRAND_CREATION_FAILED.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/edit/{id}", consumes = "multipart/form-data")
	public ResponseEntity<Brand> editBrand(@PathVariable Integer id, 
			@RequestParam(required = false) String BrandName,
			@RequestParam(required = false) MultipartFile BrandImage,
			@RequestParam Boolean status) {

		Brand editBrand = BrandService.getBrandById(id);
		if (editBrand != null) {
			if (BrandImage != null) {
				String url = cloudinaryService.uploadFile(BrandImage);
				editBrand.setBrand_image(url);
			}
			if (BrandName != null) {
				editBrand.setBrand_name(BrandName);
			}
			editBrand.setStatus(status);
			editBrand = BrandService.saveBrand(editBrand);
			System.out.println("Edit Brand success.");
			return new ResponseEntity<Brand>(editBrand, HttpStatus.OK);
		} else {
			System.out.println("Edit Brand failed.");
			return new ResponseEntity<Brand>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Integer id) {
        try {
            Brand Brand = BrandService.getBrandById(id);
            if (Brand != null) {
                BrandService.deleteBrandById(id);
                System.out.println("Brand with ID " + id + " has been deleted");
                return ResponseEntity.ok("Brand with ID " + id + " has been deleted");
            } else {
                System.out.println("Brand with ID " + id + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brand with ID " + id + " not found");
            }
        } catch (Exception e) {
            System.out.println("Error deleting Brand with ID " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Brand with ID " + id);
        }
    }
}


