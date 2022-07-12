package com.example.taxiservicespring.controller.dto;

import lombok.Data;

@Data
public class CarDto {
    private long id;
    private String regNumber;
    private long modelId;
    private int categoryId;
    private long locationId;
    private String status;
}
