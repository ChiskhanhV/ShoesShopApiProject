package com.example.api.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shipping_type")
public class Shipping_Type {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(name = "shipping_name", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String shippingName;

    @Column(name = "ship_cost", nullable = false)
    private int shipCost;

    @Column(name = "estimated_time", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String estimatedTime;
    
    @OneToMany(mappedBy = "shipping_type", cascade = CascadeType.ALL)
	private List<Order> Order;
}
