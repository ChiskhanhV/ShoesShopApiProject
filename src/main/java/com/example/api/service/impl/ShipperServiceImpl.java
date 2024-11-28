package com.example.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.entity.Shipper;
import com.example.api.repository.ShipperRepository;
import com.example.api.service.ShipperService;

@Service
public class ShipperServiceImpl implements ShipperService{
	@Autowired
	ShipperRepository ShipperRepository;

	@Override
	public Shipper saveShipper(Shipper Shipper) {
		// TODO Auto-generated method stub
		return ShipperRepository.save(Shipper);
	}

	@Override
	public List<Shipper> getAllShipper() {
		 //TODO Auto-generated method stub
		return ShipperRepository.findAll();
	}

	@Override
	public void deleteShipperById(String id) {
		ShipperRepository.deleteById(id);
	}


	@Override
	public Shipper getShipperByID(String id) throws Exception {
		// TODO Auto-generated method stub
		return ShipperRepository.findById(id).orElseThrow(() -> new Exception("Shipper not found with ID: " + id));
	}

}
