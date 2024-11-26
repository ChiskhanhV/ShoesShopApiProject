package com.example.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.entity.Shipping_Company;
import com.example.api.repository.Shipping_CompanyRepository;
import com.example.api.service.Shipping_CompanyService;

@Service
public class Shipping_CompanyServiceImpl implements Shipping_CompanyService{
	@Autowired
	Shipping_CompanyRepository Shipping_CompanyRepository;

	@Override
	public Shipping_Company saveShipping_Company(Shipping_Company Shipping_Company) {
		// TODO Auto-generated method stub
		return Shipping_CompanyRepository.save(Shipping_Company);
	}

	@Override
	public Shipping_Company getShipping_CompanyById(int id) {
		// TODO Auto-generated method stub
		return Shipping_CompanyRepository.getById(id);
	}

	@Override
	public List<Shipping_Company> getAllShipping_Company() {
		 //TODO Auto-generated method stub
		return Shipping_CompanyRepository.findAll();
	}

	@Override
	public void deleteShipping_CompanyById(int id) {
		Shipping_CompanyRepository.deleteById(id);
	}

}
