package com.example.taxiservicespring.service.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Location {
	
	private long id;
	private String streetName;
	private String streetNumber;
	private BigDecimal latitude;
	private BigDecimal longitude;

}
