package com.example.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.api.entity.Promotion_Item;
import com.example.api.repository.Promotion_ItemRepository;
import com.example.api.service.Promotion_ItemService;

@Service
public class Promotion_ItemServiceImpl implements Promotion_ItemService{

	@Autowired
	Promotion_ItemRepository itemRepository;
	
	@Override
	public Promotion_Item savePromotion_Item(Promotion_Item promotion_Item) {
		return itemRepository.save(promotion_Item);
	}

	@Override
	public void deleteById(int id) {
		itemRepository.deleteById(id);	
	}

	@Override
	public void deleteByPromotionId(int id) {
		itemRepository.deleteByPromotionId(id);
	}

	@Override
	public Promotion_Item getPromotionItemById(int id) {
		return itemRepository.getById(id);
	}

}
