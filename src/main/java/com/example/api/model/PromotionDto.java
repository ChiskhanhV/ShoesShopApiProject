package com.example.api.model;

import java.sql.Date;

import lombok.Data;

@Data
public class PromotionDto {
	private int id;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private int discount;
	private int isActive;

	public static void applyPromotion(Promotion_ItemDto itemDto, PromotionDto promotionDto) {
		Date currentTime = new Date(System.currentTimeMillis());
		itemDto.updateDiscountBasedOnPromotion(currentTime, promotionDto.getStartDate(), promotionDto.getEndDate(),
				promotionDto.getDiscount(), promotionDto.getIsActive());
	}
}
