package com.example.api.entity;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data // lombok giúp generate các hàm constructor, get, set v.v.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`order`")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "total")
	private int total;
	
	@Column(name = "booking_date")
	private Date booking_Date;
	
	@Column(name = "payment_method", columnDefinition = "nvarchar(1111)")
	private String payment_Method;
	
	@Column(name = "status", columnDefinition = "nvarchar(1111)")
	private String status;
	
	@Column(name = "fullname", columnDefinition = "nvarchar(1111)")
	private String fullname;
	
	@Column(name = "country", columnDefinition = "nvarchar(1111)")
	private String country;
	
	@Column(name = "address", columnDefinition = "nvarchar(1111)")
	private String address;
	
	@Column(name = "phone", columnDefinition = "nvarchar(1111)")
	private String phone;
	
	@Column(name = "email", columnDefinition = "nvarchar(1111)")
	private String email;
	
	@Column(name = "note", columnDefinition = "nvarchar(1111)")
	private String note;
	
	@Column(name = "is_pay", columnDefinition = "TINYINT(1) DEFAULT 0")
	private Boolean isPay = false;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<Order_Item> order_Item;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "voucher_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	private Voucher voucher;
	
	@ManyToOne
	@JoinColumn(name = "shipping_type_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	private Shipping_Type shipping_type;
	
	@ManyToOne
	@JoinColumn(name = "shipper_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	private Shipper shipper;

	 
	
}