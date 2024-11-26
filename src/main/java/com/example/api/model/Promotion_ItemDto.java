package com.example.api.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Promotion_ItemDto {
	private int id;
	private int discount;

	public void updateDiscountBasedOnPromotion(Date currentTime, Date startDate, Date endDate, int promotionDiscount,
			int isActive) {
		if ((currentTime.before(startDate) || currentTime.after(endDate)) && isActive == 1 ) {
			this.discount = 0;
		} else {
			this.discount = promotionDiscount;
		}
	}
}
