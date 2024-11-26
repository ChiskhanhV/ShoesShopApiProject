package com.example.api.service;

import java.util.List;

import com.example.api.entity.Order_Item;

public interface Order_ItemService {
	List<Order_Item> getAllByOrder_Id(int id);
	
	Order_Item saveOrder_Item(Order_Item order_Item);
	
	void deleteById(int id);
}
