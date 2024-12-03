package com.example.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
	Product findById(int id);

	Page<Product> findAll(Pageable pageable);

	@Query(value = "Select * From product p where sold > 0 ORDER BY p.sold DESC LIMIT 10;", nativeQuery = true)
	List<Product> findTop12ProductBestSellers();

	@Query(value = "Select * From product p ORDER BY p.created_at DESC LIMIT 10;", nativeQuery = true)
	List<Product> findTop12ProductNewArrivals();

	@Query(value = "select * from product p where p.id in (select i.product_id from promotion pro inner join promotion_item i on pro.id = i.promotion_id and pro.is_active = 1 and current_date() between pro.start_date and pro.end_date);", nativeQuery = true)
	List<Product> findProductIsOnSale();
	
	@Query(value = "SELECT * FROM product WHERE product_name LIKE %:name%", nativeQuery = true)
    Page<Product> findByProductNameContaining(@Param("name") String name, Pageable pageable);
	
	@Query(value = "select * from product p where p.id not in (select i.product_id from promotion pro inner join promotion_item i on pro.id = i.promotion_id);", nativeQuery = true)
    List<Product> findByProductNotInPromotion();
	
	@Query(value = "select * from product p where p.id in (select i.product_id from promotion pro inner join promotion_item i on pro.id = i.promotion_id and i.promotion_id = ?1);", nativeQuery = true)
	List<Product> findProductByPromotionID(int id);
	
	@Query(value = "SELECT p.* FROM product p " +
            "INNER JOIN promotion_item i ON p.id = i.product_id " +
            "INNER JOIN promotion pro ON pro.id = i.promotion_id " +
            "WHERE p.id = :productId " +
            "AND pro.is_active = 1 " +
            "AND CURRENT_DATE BETWEEN pro.start_date AND pro.end_date", 
    nativeQuery = true)
	Product findProductInPromotion(@Param("productId") int productId);

}
