package com.example.api.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
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

import com.example.api.entity.Rating;
import com.example.api.entity.Product;
import com.example.api.entity.User;
import com.example.api.model.RatingDto;
import com.example.api.service.RatingService;
import com.example.api.service.ProductService;
import com.example.api.service.UserService;
import com.example.api.service.CloudinaryService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/ratings")
public class RatingController {
	@Autowired
	RatingService RatingService;
	
	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;
	
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CloudinaryService cloudinaryService;

	@GetMapping
	public ResponseEntity<List<Rating>> GetRating() {
		List<Rating> listRatings = RatingService.getAllRating();
		if (listRatings != null) {
			return new ResponseEntity<>(listRatings, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(listRatings, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@GetMapping("/product/{productId}")
//    public ResponseEntity<List<Rating>> getRatingsByProductId(@PathVariable int productId) {
//        List<Rating> listRatings = RatingService.getRatingByProductId(productId);
//        if (listRatings != null) {
//			return new ResponseEntity<>(listRatings, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(listRatings, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//    }
	
	@GetMapping("/pagination/{page}/{pageSize}")
	public ResponseEntity<Page<Rating>> findProductsWithPagination(@PathVariable int page,@PathVariable int pageSize){
		Page<Rating> categories = RatingService.getRatingByPagination(page, pageSize);
		return new ResponseEntity<>(categories, HttpStatus.OK); 
	}

	@PostMapping(path = "/add", consumes = "multipart/form-data")
	public ResponseEntity<Rating> newRating(@RequestParam String user_id, @RequestParam int product_id, @RequestParam int ratingValue,
			@RequestParam String review,
			@RequestParam(required = false) MultipartFile RatingImage) {
		Rating newRating = new Rating();
		User user = userService.findByIdAndRole(user_id, "user");
		Product product = productService.getProductById(product_id);
		newRating.setProduct(product);
		newRating.setUser(user);
		newRating.setRatingValue(ratingValue);

		if (RatingImage != null) {
			String url = cloudinaryService.uploadFile(RatingImage);
			newRating.setImg(url);
		} else {
		    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		newRating.setReview(review);
		long millis = System.currentTimeMillis();
		Date ratedDate = new java.sql.Date(millis);
		newRating.setRatedAt(ratedDate);
		newRating = RatingService.saveRating(newRating);
		if (newRating != null) {
			System.out.println("New Rating add success.");
			return new ResponseEntity<>(newRating, HttpStatus.OK);
		} else {
			System.out.println("New Rating add failed.");
			return new ResponseEntity<>(newRating, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@PutMapping(path = "/edit/{id}", consumes = "multipart/form-data")
	public ResponseEntity<Rating> editRating(@PathVariable int id, @RequestParam String user_id, @RequestParam int product_id, 
			@RequestParam int ratingValue,
			@RequestParam String review,
			@RequestParam(required = false) List<MultipartFile> RatingImage) {

		Rating newRating = new Rating();
		User user = userService.findByIdAndRole(user_id, "user");
		Product product = productService.getProductById(product_id);
		newRating.setProduct(product);
		newRating.setUser(user);
		newRating.setRatingValue(ratingValue);

		if (RatingImage != null && !RatingImage.isEmpty()) {
		    StringBuilder urls = new StringBuilder();
		    for (MultipartFile image : RatingImage) {
		        String url = cloudinaryService.uploadFile(image);
		        urls.append(url).append(","); // Lưu nhiều URL, ngăn cách bằng dấu phẩy
		    }
		    newRating.setImg(urls.toString());
		} else {
		    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		newRating.setReview(review);
		long millis = System.currentTimeMillis();
		Date ratedDate = new java.sql.Date(millis);
		newRating.setRatedAt(ratedDate);
		newRating = RatingService.saveRating(newRating);
		System.out.println("Edit Rating success.");
		return new ResponseEntity<Rating>(newRating, HttpStatus.OK);
	}
	
	@GetMapping("/average-rating/{productId}")
	public ResponseEntity<Double> getAverageRating(@PathVariable int productId) {
	    Double averageRating = RatingService.getAverageRatingForProduct(productId);
	    return new ResponseEntity<>(averageRating, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRating(@PathVariable Integer id) {
        try {
            Rating Rating = RatingService.getRatingById(id);
            if (Rating != null) {
                RatingService.deleteRatingById(id);
                System.out.println("Rating with ID " + id + " has been deleted");
                return ResponseEntity.ok("Rating with ID " + id + " has been deleted");
            } else {
                System.out.println("Rating with ID " + id + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rating with ID " + id + " not found");
            }
        } catch (Exception e) {
            System.out.println("Error deleting Rating with ID " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Rating with ID " + id);
        }
    }
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<RatingDto>> getRatingsByProductId(@PathVariable int productId) {
		List<Rating> listRatings = RatingService.getRatingByProductId(productId);

		List<RatingDto> listRatingDto = new ArrayList<>();
		for (Rating o : listRatings) {
			RatingDto dto = modelMapper.map(o, RatingDto.class);
			listRatingDto.add(dto);
		}
		return new ResponseEntity<>(listRatingDto, HttpStatus.OK);
	}

}
