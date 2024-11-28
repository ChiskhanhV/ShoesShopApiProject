package com.example.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entity.Shipper;

public interface ShipperRepository extends JpaRepository<Shipper, String> {
	void deleteById(String id);
	
//	Shipper findById(String id);
	
	List<Shipper> findAll();

	Shipper save(Shipper shipper);
}
