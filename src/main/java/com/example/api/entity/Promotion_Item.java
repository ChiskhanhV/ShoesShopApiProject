package com.example.api.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "promotion_item")
public class Promotion_Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "promotion_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	private Promotion promotion;
	
	@JsonProperty("discount")
    public int getDiscount() {
        if (promotion != null && isPromotionActive() && promotion.getIs_Active() == 1) {
            return promotion.getDiscount();
        }
        return 0;
    }

    private boolean isPromotionActive() {
        Date now = new Date();
        return promotion.getStartDate().before(now) && promotion.getEndDate().after(now);
    }
}

