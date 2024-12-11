package com.example.api.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entity.Product;
import com.example.api.entity.Promotion;
import com.example.api.entity.Promotion_Item;
import com.example.api.error.ErrorCode;
import com.example.api.error.ErrorResponse;
import com.example.api.model.PromotionDto;
import com.example.api.service.ProductService;
import com.example.api.service.PromotionService;
import com.example.api.service.Promotion_ItemService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/promotion")
public class PromotionController {
	@Autowired
	PromotionService promotionService;

	@Autowired
	Promotion_ItemService promotion_ItemService;

	@Autowired
	ProductService productService;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<Page<Promotion>> getAllPromotion(@RequestParam int page, @RequestParam int pageSize) {
		Page<Promotion> promos = promotionService.findAll(page, pageSize);
		return new ResponseEntity<>(promos, HttpStatus.OK);
	}

	@GetMapping("/promodetail/{id}")
	public ResponseEntity<Promotion> getPromotionByID(@PathVariable int id) {
		Promotion promotion = promotionService.getPromotionById(id);
		return new ResponseEntity<Promotion>(promotion, HttpStatus.OK);
	}

	@GetMapping(path = "/checkproduct/{productId}")
	public ResponseEntity<PromotionDto> getPromotionByProductID(@PathVariable("productId") int productId) {
		Promotion promotion = promotionService.getPromotionByProductId(productId);

		if (promotion != null) {
			PromotionDto promotionDto = modelMapper.map(promotion, PromotionDto.class);
			return new ResponseEntity<>(promotionDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/add")
	public ResponseEntity<?> newPromotion(@RequestParam String name, @RequestParam String description,
			@RequestParam String start, @RequestParam String end, @RequestParam Integer discount,
			@RequestParam Integer status, @RequestParam String productIds) throws Exception {

		Promotion newPromotion = new Promotion();

		newPromotion.setName(name);
		newPromotion.setDescription(description);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate;
		Date endDate;

		try {
			startDate = new Date(dateFormat.parse(start).getTime());
			endDate = new Date(dateFormat.parse(end).getTime());
		} catch (ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// Kiểm tra ngày hết hạn phải sau ngày bắt đầu
	    if (!endDate.after(startDate)) {
	        return new ResponseEntity<>(new ErrorResponse(ErrorCode.EndDateBeforeStartDateException.getMessage()), HttpStatus.BAD_REQUEST);
	    }
		newPromotion.setStartDate(startDate);
		newPromotion.setEndDate(endDate);
		newPromotion.setDiscount(discount);
		newPromotion.setIs_Active(status);
		Promotion savedPromotion = promotionService.savePromotion(newPromotion);

		ObjectMapper objectMapper = new ObjectMapper();
		List<Integer> productIdList = objectMapper.readValue(productIds, new TypeReference<List<Integer>>() {
		});

		for (Integer id : productIdList) {
			Promotion_Item newItem = new Promotion_Item();
			Product product = productService.getProductById(id);
			newItem.setPromotion(newPromotion);
			newItem.setProduct(product);
			promotion_ItemService.savePromotion_Item(newItem);
		}

		return new ResponseEntity<>(savedPromotion, HttpStatus.OK);
	}

	@PostMapping(path = "/edit")
	public ResponseEntity<?> editPromotion(@RequestParam int promotionId, @RequestParam String name,
			@RequestParam String description, @RequestParam String start, @RequestParam String end,
			@RequestParam Integer discount, @RequestParam Integer status, @RequestParam String productIds)
			throws Exception {

		Promotion newPromotion = promotionService.getPromotionById(promotionId);

		if (newPromotion != null) {
			newPromotion.setName(name);
			newPromotion.setDescription(description);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate;
			Date endDate;

			try {
				startDate = new Date(dateFormat.parse(start).getTime());
				endDate = new Date(dateFormat.parse(end).getTime());
			} catch (ParseException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			// Kiểm tra ngày hết hạn phải sau ngày bắt đầu
		    if (!endDate.after(startDate)) {
		        return new ResponseEntity<>(new ErrorResponse(ErrorCode.EndDateBeforeStartDateException.getMessage()), HttpStatus.BAD_REQUEST);
		    }
			newPromotion.setStartDate(startDate);
			newPromotion.setEndDate(endDate);
			newPromotion.setDiscount(discount);
			newPromotion.setIs_Active(status);
			Promotion savedPromotion = promotionService.savePromotion(newPromotion);
						
			promotion_ItemService.deleteByPromotionId(promotionId);
			
			ObjectMapper objectMapper = new ObjectMapper();
			List<Integer> productIdList = objectMapper.readValue(productIds, new TypeReference<List<Integer>>() {
			});

			for (Integer id : productIdList) {
				Promotion_Item newItem = new Promotion_Item();
				Product product = productService.getProductById(id);
				newItem.setPromotion(newPromotion);
				newItem.setProduct(product);
				promotion_ItemService.savePromotion_Item(newItem);
			}

			return new ResponseEntity<>(savedPromotion, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deletePromotion(@PathVariable Integer id) {
		try {
			Promotion promotion = promotionService.getPromotionById(id);
			if (promotion != null) {
				promotion_ItemService.deleteByPromotionId(id);
				promotionService.deleteByPromotionID(id);
				System.out.println("Promotion with ID " + id + " has been deleted");
				return ResponseEntity.ok("Promotion with ID " + id + " has been deleted");
			} else {
				System.out.println("Promotion with ID " + id + " not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Promotion with ID " + id + " not found");
			}
		} catch (Exception e) {
			System.out.println("Error deleting promotion with ID " + id);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting promotion with ID " + id);
		}
	}

}
