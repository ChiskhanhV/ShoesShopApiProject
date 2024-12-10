package com.example.api.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.api.entity.Product;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public class ProductSpecification {
	public static Specification<Product> hasCategory(String categoryName) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (categoryName != null && !categoryName.isEmpty()) {
                return criteriaBuilder.equal(root.get("category").get("category_Name"), categoryName);
            }
            return null;
        };
    }
	
	public static Specification<Product> hasBrand(String brandName) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (brandName != null && !brandName.isEmpty()) {
                return criteriaBuilder.equal(root.get("category").get("category_Name"), brandName);
            }
            return null;
        };
    }
	
	public static Specification<Product> hasSize(Integer size) {
	    return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
	        if (size != null) {
	            // Thực hiện join với bảng product_size
	            Join<Object, Object> productSizeJoin = root.join("productSize");
	            return criteriaBuilder.equal(productSizeJoin.get("size"), size);
	        }
	        return null;
	    };
	}



    public static Specification<Product> hasPriceBetween(Integer minPrice, Integer maxPrice) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            }
            return null;
        };
    }

    public static Specification<Product> orderByPrice(String sortOrder) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if ("asc".equalsIgnoreCase(sortOrder)) {
                query.orderBy(criteriaBuilder.asc(root.get("price")));
            } else if ("desc".equalsIgnoreCase(sortOrder)) {
                query.orderBy(criteriaBuilder.desc(root.get("price")));
            }
            return null; // Specification không cần trả về Predicate nếu chỉ dùng để sắp xếp.
        };
    }
}
