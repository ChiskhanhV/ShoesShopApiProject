package com.example.api.model;

import lombok.Data;

@Data
public class VoucherDto {
    private String voucherCode; // Mã giảm giá
    private int orderTotal;        // Giá trị đơn hàng
}

