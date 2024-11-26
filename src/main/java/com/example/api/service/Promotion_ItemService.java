package com.example.api.service;

import com.example.api.entity.Promotion_Item;

public interface Promotion_ItemService {
	Promotion_Item savePromotion_Item(Promotion_Item promotion_Item);
	
	void deleteById(int id);
	
	void deleteByPromotionId(int id);
	
	Promotion_Item getPromotionItemById(int id);
}
