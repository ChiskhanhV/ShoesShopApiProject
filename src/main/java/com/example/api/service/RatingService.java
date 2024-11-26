package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.api.entity.Rating;

public interface RatingService {
	Rating saveRating(Rating Rating);
	Rating getRatingById(int id);
	List<Rating> getRatingByProductId(int ProductId);
	Optional<Rating> findByUserIdAndProductId(int userId, int productId);
	List<Rating> getAllRating();
	void deleteRatingById(int id);
	Page<Rating> getRatingByPagination(int page, int pageSize);
	Double getAverageRatingForProduct(int productId);
}
