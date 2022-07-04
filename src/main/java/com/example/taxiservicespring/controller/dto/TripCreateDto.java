package com.example.taxiservicespring.controller.dto;

import lombok.Data;

@Data
public class TripCreateDto {
	
	private long personId;
	private long originId;
	private long destinationId;
	private int categoryId;
	private int capacity;
	boolean multipleCars;
	boolean ignoreCategory;

}
