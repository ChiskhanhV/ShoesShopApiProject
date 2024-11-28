package com.example.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.entity.Shipping_Type;
import com.example.api.repository.Shipping_TypeRepository;
import com.example.api.service.Shipping_TypeService;

@Service
public class Shipping_TypeServiceImpl implements Shipping_TypeService{
	@Autowired
	Shipping_TypeRepository Shipping_TypeRepository;

	@Override
	public Shipping_Type saveShipping_Type(Shipping_Type Shipping_Type) {
		// TODO Auto-generated method stub
		return Shipping_TypeRepository.save(Shipping_Type);
	}

	@Override
	public Shipping_Type getShipping_TypeById(int id) {
		// TODO Auto-generated method stub
		return Shipping_TypeRepository.getById(id);
	}

	@Override
	public List<Shipping_Type> getAllShipping_Type() {
		 //TODO Auto-generated method stub
		return Shipping_TypeRepository.findAll();
	}

	@Override
	public void deleteShipping_TypeById(int id) {
		Shipping_TypeRepository.deleteById(id);
	}

}
