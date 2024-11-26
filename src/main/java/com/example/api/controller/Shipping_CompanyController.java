package com.example.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entity.Shipping_Company;
import com.example.api.service.Shipping_CompanyService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/Shipping_Company")
public class Shipping_CompanyController {
	@Autowired
	Shipping_CompanyService Shipping_CompanyService;

	@GetMapping
	public ResponseEntity<List<Shipping_Company>> GetShipping_Company() {
		List<Shipping_Company> listShipping_Companys = Shipping_CompanyService.getAllShipping_Company();
		if (listShipping_Companys != null) {
			return new ResponseEntity<>(listShipping_Companys, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(listShipping_Companys, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@GetMapping("/pagination/{page}/{pageSize}")
//	public ResponseEntity<Page<Shipping_Company>> findProductsWithPagination(@PathVariable int page,@PathVariable int pageSize){
//		Page<Shipping_Company> categories = Shipping_CompanyService.getShipping_CompanyByPagination(page, pageSize);
//		return new ResponseEntity<>(categories, HttpStatus.OK); 
//	}

	@PostMapping(path = "/add")
	public ResponseEntity<Shipping_Company> newShipping_Company(@RequestParam String Shipping_CompanyName,
			@RequestParam int shipCost, @RequestParam String estimateTime) {
		Shipping_Company newShipping_Company = new Shipping_Company();
		newShipping_Company.setShippingName(Shipping_CompanyName);
		newShipping_Company.setShipCost(shipCost);
		newShipping_Company.setEstimatedTime(estimateTime);
		newShipping_Company = Shipping_CompanyService.saveShipping_Company(newShipping_Company);
		if (newShipping_Company != null) {
			System.out.println("New Shipping_Company add success.");
			return new ResponseEntity<>(newShipping_Company, HttpStatus.OK);
		} else {
			System.out.println("New Shipping_Company add failed.");
			return new ResponseEntity<>(newShipping_Company, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/edit/{id}")
	public ResponseEntity<Shipping_Company> editShipping_Company(@PathVariable Integer id, 
			@RequestParam String Shipping_CompanyName,
			@RequestParam int shipCost, @RequestParam String estimateTime) {

		Shipping_Company newShipping_Company = Shipping_CompanyService.getShipping_CompanyById(id);
		if (newShipping_Company != null) {
			newShipping_Company.setShippingName(Shipping_CompanyName);
			newShipping_Company.setShipCost(shipCost);
			newShipping_Company.setEstimatedTime(estimateTime);
			newShipping_Company = Shipping_CompanyService.saveShipping_Company(newShipping_Company);
			System.out.println("Edit Shipping_Company success.");
			return new ResponseEntity<Shipping_Company>(newShipping_Company, HttpStatus.OK);
		} else {
			System.out.println("Edit Shipping_Company failed.");
			return new ResponseEntity<Shipping_Company>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShipping_Company(@PathVariable Integer id) {
        try {
            Shipping_Company Shipping_Company = Shipping_CompanyService.getShipping_CompanyById(id);
            if (Shipping_Company != null) {
                Shipping_CompanyService.deleteShipping_CompanyById(id);
                System.out.println("Shipping_Company with ID " + id + " has been deleted");
                return ResponseEntity.ok("Shipping_Company with ID " + id + " has been deleted");
            } else {
                System.out.println("Shipping_Company with ID " + id + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shipping_Company with ID " + id + " not found");
            }
        } catch (Exception e) {
            System.out.println("Error deleting Shipping_Company with ID " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Shipping_Company with ID " + id);
        }
    }
}
