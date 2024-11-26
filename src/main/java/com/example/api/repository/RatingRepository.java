package com.example.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.api.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
	@Query(value ="SELECT r FROM Rating r JOIN r.product p WHERE p.id = ?1", nativeQuery = true )
    List<Rating> findByProductId(int productId);

    @Query(value = "SELECT r FROM Rating r JOIN r.user u JOIN r.product p WHERE u.id = ?1 AND p.id = ?2", nativeQuery = true)
    Optional<Rating> findByUserIdAndProductId(int userId, int productId);
    
    @Query(value = "SELECT AVG(r.rating_value) FROM Rating r WHERE r.product_id = ?1", nativeQuery = true)
    Double findAverageRatingByProductId(int productId);

    Page<Rating> findAll(Pageable pageable);
}

