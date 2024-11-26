package com.example.api.model;

import lombok.Data;
import java.sql.Date;
import java.util.List;

@Data
public class ProductDto {
	private int id;
	private String product_Name;
	private String description;
	private int sold;
	private int is_Active;
	private Date created_At;
	private int price;
	private List<Product_ImageDto> productImage;
	private List<Product_SizeDto> productSize;
	private List<Promotion_ItemDto> promotion_Item;
}
