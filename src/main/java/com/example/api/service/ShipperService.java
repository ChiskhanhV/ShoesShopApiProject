package com.example.api.service;

import java.util.List;

import com.example.api.entity.Shipper;

public interface ShipperService {
	Shipper saveShipper(Shipper Shipper);
	List<Shipper> getAllShipper();
	void deleteShipperById(String id);
	Shipper getShipperByID(String id);
}
