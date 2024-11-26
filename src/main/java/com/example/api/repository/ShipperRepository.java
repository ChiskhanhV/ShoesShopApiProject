package com.example.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entity.Shipper;

public interface ShipperRepository extends JpaRepository<Shipper, String> {
	void deleteById(String id);
	
	Shipper getById(String id);
	
	List<Shipper> findAll();

	Shipper save(Shipper shipper);
}
