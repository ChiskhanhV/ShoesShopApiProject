package com.example.api.model;

import java.util.Date;

import lombok.Data;
@Data
public class ShippingDto {
	private int id;
	private Date delivered_date;
	private String status;
	private OrderDto order;
	private ShipperDto shipper;
}
