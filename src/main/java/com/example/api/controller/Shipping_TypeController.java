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

import com.example.api.entity.Shipping_Type;
import com.example.api.service.Shipping_TypeService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/Shipping_Type")
public class Shipping_TypeController {
	@Autowired
	Shipping_TypeService Shipping_TypeService;

	@GetMapping
	public ResponseEntity<List<Shipping_Type>> GetShipping_Type() {
		List<Shipping_Type> listShipping_Types = Shipping_TypeService.getAllShipping_Type();
		if (listShipping_Types != null) {
			return new ResponseEntity<>(listShipping_Types, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(listShipping_Types, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@GetMapping("/pagination/{page}/{pageSize}")
//	public ResponseEntity<Page<Shipping_Type>> findProductsWithPagination(@PathVariable int page,@PathVariable int pageSize){
//		Page<Shipping_Type> categories = Shipping_TypeService.getShipping_TypeByPagination(page, pageSize);
//		return new ResponseEntity<>(categories, HttpStatus.OK); 
//	}

	@PostMapping(path = "/add")
	public ResponseEntity<Shipping_Type> newShipping_Type(@RequestParam String Shipping_TypeName,
			@RequestParam int shipCost, @RequestParam String estimateTime) {
		Shipping_Type newShipping_Type = new Shipping_Type();
		newShipping_Type.setShippingName(Shipping_TypeName);
		newShipping_Type.setShipCost(shipCost);
		newShipping_Type.setEstimatedTime(estimateTime);
		newShipping_Type = Shipping_TypeService.saveShipping_Type(newShipping_Type);
		if (newShipping_Type != null) {
			System.out.println("New Shipping_Type add success.");
			return new ResponseEntity<>(newShipping_Type, HttpStatus.OK);
		} else {
			System.out.println("New Shipping_Type add failed.");
			return new ResponseEntity<>(newShipping_Type, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/edit/{id}")
	public ResponseEntity<Shipping_Type> editShipping_Type(@PathVariable Integer id, 
			@RequestParam String Shipping_TypeName,
			@RequestParam int shipCost, @RequestParam String estimateTime) {

		Shipping_Type newShipping_Type = Shipping_TypeService.getShipping_TypeById(id);
		if (newShipping_Type != null) {
			newShipping_Type.setShippingName(Shipping_TypeName);
			newShipping_Type.setShipCost(shipCost);
			newShipping_Type.setEstimatedTime(estimateTime);
			newShipping_Type = Shipping_TypeService.saveShipping_Type(newShipping_Type);
			System.out.println("Edit Shipping_Type success.");
			return new ResponseEntity<Shipping_Type>(newShipping_Type, HttpStatus.OK);
		} else {
			System.out.println("Edit Shipping_Type failed.");
			return new ResponseEntity<Shipping_Type>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteShipping_Type(@PathVariable Integer id) {
        try {
            Shipping_Type Shipping_Type = Shipping_TypeService.getShipping_TypeById(id);
            if (Shipping_Type != null) {
                Shipping_TypeService.deleteShipping_TypeById(id);
                System.out.println("Shipping_Type with ID " + id + " has been deleted");
                return ResponseEntity.ok("Shipping_Type with ID " + id + " has been deleted");
            } else {
                System.out.println("Shipping_Type with ID " + id + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shipping_Type with ID " + id + " not found");
            }
        } catch (Exception e) {
            System.out.println("Error deleting Shipping_Type with ID " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Shipping_Type with ID " + id);
        }
    }
}
