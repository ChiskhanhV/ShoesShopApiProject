package com.example.api.controller;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.api.service.VnPayService;
import com.fasterxml.jackson.databind.ObjectMapper;

import momo.*;
import com.example.api.config.MomoConfig;

import jakarta.servlet.http.HttpSession;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/payment")
public class PaymentController {
	@Autowired
    private VnPayService vnpayService;

    @GetMapping("/vnpay")
    public ResponseEntity<String> createPayment(@RequestParam("amount") long amount) {
        String paymentURL =  vnpayService.createPaymentUrl(amount);
        return new ResponseEntity<String>(paymentURL, HttpStatus.OK);
    }
    
    @PostMapping("/momo")
    public ResponseEntity<?> processPaymentWithMomo(@RequestParam int orderTotal) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            int code = (int) Math.floor(((Math.random() * 89999999) + 10000000));
            String orderId = Integer.toString(code);

            // Tạo yêu cầu thanh toán Momo
            MomoModel jsonRequest = new MomoModel();
            jsonRequest.setPartnerCode(MomoConfig.IDMOMO);
            jsonRequest.setOrderId(orderId);
            jsonRequest.setStoreId(orderId);
            jsonRequest.setRedirectUrl(MomoConfig.redirectUrl);
            jsonRequest.setIpnUrl(MomoConfig.ipnUrl);
            jsonRequest.setAmount(String.valueOf(orderTotal));
            jsonRequest.setOrderInfo("Thanh toán ShoesShop.");
            jsonRequest.setRequestId(orderId);
            jsonRequest.setOrderType(MomoConfig.orderType);
            jsonRequest.setRequestType(MomoConfig.requestType);
            jsonRequest.setTransId("1");
            jsonRequest.setResultCode("200");
            jsonRequest.setMessage("");
            jsonRequest.setPayType(MomoConfig.payType);
            jsonRequest.setResponseTime("300000");
            jsonRequest.setExtraData("");

            // Tạo chữ ký (signature)
            String decode = "accessKey=" + MomoConfig.accessKey + "&amount=" + jsonRequest.getAmount() + "&extraData=" 
                    + jsonRequest.getExtraData() + "&ipnUrl=" + MomoConfig.ipnUrl + "&orderId=" + orderId + "&orderInfo=" 
                    + jsonRequest.getOrderInfo() + "&partnerCode=" + jsonRequest.getPartnerCode() + "&redirectUrl=" 
                    + MomoConfig.redirectUrl + "&requestId=" + jsonRequest.getRequestId() + "&requestType=" 
                    + MomoConfig.requestType;

            String signature = MomoConfig.encode(MomoConfig.serectkey, decode);
            jsonRequest.setSignature(signature);

            // Gửi yêu cầu đến API Momo
            String json = mapper.writeValueAsString(jsonRequest);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(MomoConfig.Url))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .headers("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ResultMoMo res = mapper.readValue(response.body(), ResultMoMo.class);

            // Kiểm tra kết quả từ Momo
            if (res == null || res.getPayUrl() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thanh toán thất bại");
            }

            return ResponseEntity.ok(res.getPayUrl());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra trong quá trình xử lý thanh toán");
        }
    }
}
