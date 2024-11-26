package com.example.api.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entity.Voucher;
import com.example.api.model.VoucherDto;
import com.example.api.service.VoucherService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/vouchers")
public class VoucherController {
	@Autowired
	VoucherService VoucherService;

	@GetMapping
	public ResponseEntity<List<Voucher>> GetVoucher() {
		List<Voucher> listVouchers = VoucherService.getAllVoucher();
		if (listVouchers != null) {
			return new ResponseEntity<>(listVouchers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(listVouchers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@GetMapping("/pagination/{page}/{pageSize}")
//	public ResponseEntity<Page<Voucher>> findProductsWithPagination(@PathVariable int page,@PathVariable int pageSize){
//		Page<Voucher> categories = VoucherService.getVoucherByPagination(page, pageSize);
//		return new ResponseEntity<>(categories, HttpStatus.OK); 
//	}
	@PostMapping("/use")
    public ResponseEntity<Integer> validateVoucherUsage(
            @RequestParam String voucherCode,
            @RequestParam int orderTotal) {
        try {
            int newOrderTotal = VoucherService.validateVoucherUsage(voucherCode, orderTotal);
            return ResponseEntity.ok(newOrderTotal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

	
	@PostMapping(path = "/add")
	public ResponseEntity<Voucher> newVoucher(@RequestParam String Code, @RequestParam Integer discount, @RequestParam Integer limitNumber,
			@RequestParam String experitionDate, @RequestParam Integer paymentLimit, @RequestParam String description, @RequestParam Boolean status) throws Exception {
		Voucher newVoucher = new Voucher();
		newVoucher.setCode(Code);
		newVoucher.setDiscount(discount);
		newVoucher.setDescription(description);
		newVoucher.setLimitNumber(limitNumber);
		newVoucher.setPaymentLimit(paymentLimit);
		newVoucher.setStatus(status);
		long millis = System.currentTimeMillis();
		Date createdDate = new java.sql.Date(millis);
		newVoucher.setCreated(createdDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date expDate;
		try {
			expDate = new Date(dateFormat.parse(experitionDate).getTime());
		} catch (ParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		newVoucher.setExpirationDate(expDate);
		if (newVoucher.getExpirationDate().before(newVoucher.getCreated())) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		newVoucher.setDiscount(discount);

		newVoucher = VoucherService.saveVoucher(newVoucher);
		if (newVoucher != null) {
			System.out.println("New Voucher add success.");
			return new ResponseEntity<>(newVoucher, HttpStatus.OK);
		} else {
			System.out.println("New Voucher add failed.");
			return new ResponseEntity<>(newVoucher, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping(path = "/edit/{id}")
	public ResponseEntity<Voucher> editVoucher(@PathVariable Integer id, 
			@RequestParam String Code, @RequestParam Integer discount, @RequestParam Integer limitNumber,
			@RequestParam String experitionDate, @RequestParam Integer paymentLimit, @RequestParam String description,
			@RequestParam Boolean status) throws Exception {

		Voucher newVoucher = VoucherService.getVoucherById(id);
		if (newVoucher != null) {
			newVoucher.setCode(Code);
			newVoucher.setDiscount(discount);
			newVoucher.setDescription(description);
			newVoucher.setLimitNumber(limitNumber);
			newVoucher.setPaymentLimit(paymentLimit);
			newVoucher.setStatus(status);
			long millis = System.currentTimeMillis();
			Date createdDate = new java.sql.Date(millis);
			newVoucher.setCreated(createdDate);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date expDate;
			try {
				expDate = new Date(dateFormat.parse(experitionDate).getTime());
			} catch (ParseException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			newVoucher.setExpirationDate(expDate);
			if (newVoucher.getExpirationDate().before(newVoucher.getCreated())) {
		        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		    }
			newVoucher.setDiscount(discount);
			newVoucher = VoucherService.saveVoucher(newVoucher);
			System.out.println("Edit Voucher success.");
			return new ResponseEntity<Voucher>(newVoucher, HttpStatus.OK);
		} else {
			System.out.println("Edit Voucher failed.");
			return new ResponseEntity<Voucher>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVoucher(@PathVariable Integer id) {
        try {
            Voucher Voucher = VoucherService.getVoucherById(id);
            if (Voucher != null) {
                VoucherService.deleteByVoucherID(id);
                System.out.println("Voucher with ID " + id + " has been deleted");
                return ResponseEntity.ok("Voucher with ID " + id + " has been deleted");
            } else {
                System.out.println("Voucher with ID " + id + " not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Voucher with ID " + id + " not found");
            }
        } catch (Exception e) {
            System.out.println("Error deleting Voucher with ID " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Voucher with ID " + id);
        }
    }
}
