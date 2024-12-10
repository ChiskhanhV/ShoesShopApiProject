package com.example.api.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.api.entity.Product;
import com.example.api.entity.Promotion;
import com.example.api.entity.Promotion_Item;
import com.example.api.repository.ProductRepository;
import com.example.api.service.ProductService;
import com.example.api.specification.ProductSpecification;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productRepository;

	@Override
	public List<Product> getAllProduct() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	public Product saveProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}

	@Override
	public Product getProductById(int id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id);
	}

	@Override
	public void deleteProductById(int id) {
		productRepository.deleteById(id);
	}

	@Override
	public List<Product> findTop12ProductBestSellers() {
		// TODO Auto-generated method stub
		return productRepository.findTop12ProductBestSellers();
	}

	@Override
	public List<Product> findTop12ProductNewArrivals() {
		// TODO Auto-generated method stub
		return productRepository.findTop12ProductNewArrivals();
	}

	@Override
	public Page<Product> findProductsWithPagination(int page, int pageSize) {
		Page<Product> products = productRepository.findAll(PageRequest.of(page, pageSize));
		return products;
	}

	@Override
	public List<Product> findProductIsOnSale() {
		// TODO Auto-generated method stub
		return productRepository.findProductIsOnSale();
	}

	@Override
	public Page<Product> findByProduct_NameContaining(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return productRepository.findByProductNameContaining(name, pageable);
	}

	@Override
	public Page<Product> filterProducts(String categoryName, String brandName, Integer size, Integer minPrice, Integer maxPrice, String sortOrder, int page,
			int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Specification<Product> spec = Specification.where(null);

		if (categoryName != null && !categoryName.isEmpty()) {
			spec = spec.and(ProductSpecification.hasCategory(categoryName));
		}
		
		if (brandName != null && !brandName.isEmpty()) {
			spec = spec.and(ProductSpecification.hasBrand(brandName));
		}
		System.out.println("SIZE GIAY DEFAULT: " +size);
		if(size != null) {
			spec = spec.and(ProductSpecification.hasSize(size));
		}

		if (minPrice != null && maxPrice != null) {
			spec = spec.and(ProductSpecification.hasPriceBetween(minPrice, maxPrice));
		}

		spec = spec.and(ProductSpecification.orderByPrice(sortOrder));

		return productRepository.findAll(spec, pageable);
	}

	@Override
	public List<Product> getProductNotInPromotion() {
		// TODO Auto-generated method stub
		return productRepository.findByProductNotInPromotion();
	}

	@Override
	public List<Product> getProductByPromotionID(int id) {
		// TODO Auto-generated method stub
		return productRepository.findProductByPromotionID(id);
	}

	@Override
	public int calculateDiscountedPrice(int productId) {
	    // Tìm sản phẩm từ repository
	    Product product = productRepository.findById(productId);
	    if (product == null) {
	        // Log lỗi nếu sản phẩm không tồn tại
	        System.out.println("Sản phẩm với ID: " + productId + " không tồn tại.");
	        return 0;
	    }

	    // Lấy ngày hiện tại
	    Date currentDate = new Date();

	    // Duyệt qua danh sách PromotionItem của sản phẩm
	    for (Promotion_Item promotionItem : product.getPromotion_Item()) {
	        Promotion promotion = promotionItem.getPromotion();

	        // Kiểm tra nếu Promotion không null và đang hoạt động
	        if (promotion != null
	                && promotion.getIs_Active() == 1
	                && promotion.getStartDate().compareTo(currentDate) <= 0
	                && promotion.getEndDate().compareTo(currentDate) >= 0) {

	            // Tính toán giá sau khuyến mãi
	            double discountPercentage = promotion.getDiscount();
	            if (discountPercentage < 0 || discountPercentage > 100) {
	                System.out.println("Khuyến mãi không hợp lệ: " + discountPercentage + "%");
	                continue;
	            }
	            int discountedPrice = (int) Math.round(product.getPrice() * (1 - discountPercentage / 100.0));
	            return discountedPrice;
	        }
	    }

	    // Không có khuyến mãi nào đang hoạt động, trả về giá gốc
	    return (int) Math.round(product.getPrice());
	}
}
