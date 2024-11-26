package com.example.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.api.entity.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
	Promotion findById(int id);
	
	@Query(value = "select p.* from promotion p inner join promotion_item i on p.id = i.promotion_id and p.is_active = 1 and current_date() between p.start_date and p.end_date where i.product_id = ?1", nativeQuery = true)
	Promotion getPromotionByProductId(int productId);
	
	Page<Promotion> findAll(Pageable pageable);
	
	void deleteById(int id);
}
