package com.example.api.model;

import lombok.Data;
import java.sql.Date;

@Data
public class RatingDto {
	private int ratingId;
	private Integer ratingValue;
	private String review;
	private Date ratedAt;
    private String img;
    private UserDto user;
}
