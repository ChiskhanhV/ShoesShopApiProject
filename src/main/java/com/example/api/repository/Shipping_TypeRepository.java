package com.example.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entity.Shipping_Type;

public interface Shipping_TypeRepository extends JpaRepository<Shipping_Type, Integer> {
	Shipping_Type getById(int id);
	
	List<Shipping_Type> findAll();
}
