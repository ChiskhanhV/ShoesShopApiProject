package com.example.api.model;

import java.util.List;

import lombok.Data;

@Data
public class ShipperDto {
	private String id;
	private String fullname;
	private String password;
	private String avatar;
	private String address;
	private List<ShippingDto> shipping;
}
