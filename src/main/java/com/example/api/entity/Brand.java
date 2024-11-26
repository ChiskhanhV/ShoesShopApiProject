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

@Entity // Đánh dấu đây là table trong db
@Data // lombok giúp generate các hàm constructor, get, set v.v.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brand")
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "brand_image", columnDefinition = "nvarchar(1111)")
	private String brand_image;
	
	@Column(name = "brand_name", nullable=false, columnDefinition = "nvarchar(255)")
	private String brand_name;
	
	@Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean status = true;

    @Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean deleted = false;
    
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
	private List<Product> product;
}
