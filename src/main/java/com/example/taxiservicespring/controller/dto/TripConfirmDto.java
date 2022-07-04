package com.example.taxiservicespring.controller.dto;

import java.math.BigDecimal;
import java.util.List;

import com.example.taxiservicespring.service.model.Car;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TripConfirmDto {
	
	private long personId;
	private long originId;
	private long destinationId;
	private int categoryId;
	private BigDecimal distance;
	private BigDecimal price;
	private BigDecimal discount;
	private BigDecimal total;
	private int waitTime;
	private List<Car> cars;

}
