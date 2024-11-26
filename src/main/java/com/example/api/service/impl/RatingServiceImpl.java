package com.example.api.service.impl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.api.entity.Rating;
import com.example.api.repository.RatingRepository;
import com.example.api.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService{
	@Autowired
	RatingRepository RatingRepository;

	@Override
	public Rating saveRating(Rating Rating) {
		// TODO Auto-generated method stub
		return RatingRepository.save(Rating);
	}

	@Override
	public List<Rating> getAllRating() {
		// TODO Auto-generated method stub
		return RatingRepository.findAll();
	}

	@Override
	public void deleteRatingById(int id) {
		RatingRepository.deleteById(id);
	}

	@Override
	public Page<Rating> getRatingByPagination(int page, int pageSize) {
		Page<Rating> rating = RatingRepository.findAll(PageRequest.of(page, pageSize));
		return rating;
	}

	@Override
	public List<Rating> getRatingByProductId(int id) {
		// TODO Auto-generated method stub
		return RatingRepository.findByProductId(id);
	}

	@Override
	public Optional<Rating> findByUserIdAndProductId(int userId, int productId) {
		// TODO Auto-generated method stub
		return RatingRepository.findByUserIdAndProductId(userId, productId);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Rating getRatingById(int id) {
		// TODO Auto-generated method stub
		return RatingRepository.getById(id);
	}

	@Override
	public Double getAverageRatingForProduct(int productId) {
		// TODO Auto-generated method stub
		Double avgRating = RatingRepository.findAverageRatingByProductId(productId);
	    return avgRating != null ? avgRating : 0.0; // Trả về 0.0 nếu chưa có rating
	}
	
}
