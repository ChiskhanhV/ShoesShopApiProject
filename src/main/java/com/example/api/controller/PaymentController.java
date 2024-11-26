package com.example.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.api.service.VnPayService;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/payment")
public class PaymentController {
	@Autowired
    private VnPayService vnpayService;

    @GetMapping("/create")
    public ResponseEntity<String> createPayment(@RequestParam("amount") long amount) {
        String paymentURL =  vnpayService.createPaymentUrl(amount);
        return new ResponseEntity<String>(paymentURL, HttpStatus.OK);
    }
    
}
