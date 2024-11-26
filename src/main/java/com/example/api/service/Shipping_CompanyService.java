package com.example.api.service;

import java.util.List;

import com.example.api.entity.Shipping_Company;

public interface Shipping_CompanyService {
	Shipping_Company saveShipping_Company(Shipping_Company Shipping_Company);
	Shipping_Company getShipping_CompanyById(int id);
	List<Shipping_Company> getAllShipping_Company();
	void deleteShipping_CompanyById(int id);
}
