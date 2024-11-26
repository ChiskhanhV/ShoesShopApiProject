package com.example.api.entity;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "product_name", columnDefinition = "nvarchar(1111)")
	private String product_Name;

	@Column(name = "description", columnDefinition = "nvarchar(11111)")
	private String description;

	@Column(name = "sold")
	private int sold;

	@Column(name = "is_active")
	private int is_Active;
	
	@Column(name = "created_at")
	private Date created_At;

	@Column(name = "price")
	private int price;

	@ManyToOne
	@JoinColumn(name = "category_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	private Category category;
	
	@JsonProperty("categoryName")
    public String getCategoryName() {
        if (category != null) {
            return category.getCategory_Name();
        }
        return null;
    }
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	private Brand brand;
	
	@JsonProperty("brandName")
    public String getbrandName() {
        if (brand != null) {
            return brand.getBrand_name();
        }
        return null;
    }
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Product_Image> productImage;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Product_Size> productSize;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Order_Item> order_Item;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Cart> cart;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Promotion_Item> promotion_Item;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Rating> rating;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Favorite_Product> favoriteProduct;
}
