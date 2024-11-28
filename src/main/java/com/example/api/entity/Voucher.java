package com.example.api.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false, columnDefinition = "nvarchar(255)")
    private String code;

    @Column(name = "discount", nullable = false)
    private int discount;

    @Column(name = "limit_number", nullable = false)
    private int limitNumber;

    @Column(name = "number_used", columnDefinition = "int default 0")
    private int numberUsed = 0;

    @Column(name = "expiration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @Column(name = "payment_limit", nullable = false)
    private int paymentLimit;

    @Column(name = "description", nullable = false, columnDefinition = "nvarchar(255)")
    private String description;

    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date created;

    @Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean deleted = false;

    @Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean status = true;
    
    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
	private List<Order> order;
}

