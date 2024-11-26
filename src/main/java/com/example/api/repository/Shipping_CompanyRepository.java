package com.example.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entity.Shipping_Company;

public interface Shipping_CompanyRepository extends JpaRepository<Shipping_Company, Integer> {
	Shipping_Company getById(int id);
	
	List<Shipping_Company> findAll();
}
