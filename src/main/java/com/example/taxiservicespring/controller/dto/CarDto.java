package com.example.taxiservicespring.controller.dto;

import com.example.taxiservicespring.service.model.CarStatus;

import lombok.Data;

@Data
public class CarDto {
    private long id;
    private String regNumber;
    private long modelId;
    private int categoryId;
    private long locationId;
    private CarStatus status;
}
