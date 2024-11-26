package com.example.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.api.entity.Promotion;
import com.example.api.repository.PromotionRepository;
import com.example.api.service.PromotionService;

@Service
public class PromotionServiceImpl implements PromotionService{
	@Autowired 
	PromotionRepository promotionRepository;

	@Override
	public Promotion savePromotion(Promotion promotion) {
		// TODO Auto-generated method stub
		return promotionRepository.save(promotion);
	}

	@Override
	public Promotion getPromotionById(int id) {
		// TODO Auto-generated method stub
		return promotionRepository.findById(id);
	}

	@Override
	public Promotion getPromotionByProductId(int productId) {
		// TODO Auto-generated method stub
		return promotionRepository.getPromotionByProductId(productId);
	}

	@Override
	public Page<Promotion> findAll(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		return promotionRepository.findAll(pageable);
	}

	@Override
	public void deleteByPromotionID(int id) {
		promotionRepository.deleteById(id);		
	}

}
