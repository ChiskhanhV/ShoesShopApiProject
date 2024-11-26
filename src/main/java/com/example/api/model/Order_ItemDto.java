package com.example.api.model;

import lombok.Data;

@Data
public class Order_ItemDto{
	private int id;
	private int count;
	private int size;
	private ProductDto product;
}
