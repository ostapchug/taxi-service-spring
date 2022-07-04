package com.example.taxiservicespring.controller.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class TripDto {
	
	@JsonProperty(access = Access.READ_ONLY)
	private long id; 
	private long personId;
	private long originId;
	private long destinationId;
	private BigDecimal distance;
	private Timestamp date;
	private BigDecimal bill;
	private String status;

}
