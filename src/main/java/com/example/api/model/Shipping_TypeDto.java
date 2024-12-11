package com.example.api.model;

import lombok.Data;

@Data
public class Shipping_TypeDto {
	private int id;
	private String shippingName;
	private int shipCost;
	private String estimatedTime;
}
