package com.example.api.service;

import org.springframework.data.domain.Page;

import com.example.api.entity.Promotion;

public interface PromotionService {
	Promotion savePromotion(Promotion promotion);

	Promotion getPromotionById(int id);
	
	Page<Promotion> findAll(int page, int pageSize);
	
	Promotion getPromotionByProductId(int productId);
	
	void deleteByPromotionID(int id);
}
