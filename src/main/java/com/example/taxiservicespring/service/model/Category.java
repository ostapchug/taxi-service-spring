package com.example.taxiservicespring.service.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Category {
	
	private int id;
	private String name;
	private BigDecimal price;

}
