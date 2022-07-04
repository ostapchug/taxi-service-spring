package com.example.taxiservicespring.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class CarDto {
	
	@JsonProperty(access = Access.READ_ONLY)
	private long id;
	private String regNumber;
	private long modelId;
	private int categoryId;
	private long locationId;
	private String status;

}
