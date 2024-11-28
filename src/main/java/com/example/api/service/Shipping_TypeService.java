package com.example.api.service;

import java.util.List;

import com.example.api.entity.Shipping_Type;

public interface Shipping_TypeService {
	Shipping_Type saveShipping_Type(Shipping_Type Shipping_Type);
	Shipping_Type getShipping_TypeById(int id);
	List<Shipping_Type> getAllShipping_Type();
	void deleteShipping_TypeById(int id);
}
