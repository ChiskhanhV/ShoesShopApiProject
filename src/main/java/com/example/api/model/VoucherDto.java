package com.example.api.model;

import java.util.Date;
import java.util.List;

import com.example.api.entity.Order;

import lombok.Data;

@Data
public class VoucherDto {
    private int id;
    private String code;
    private int discount;
    private int limitNumber;
    private int numberUsed;
    private Date expirationDate;
    private int paymentLimit;
    private String description;
    private Date created;
    private List<Order> order;
}

