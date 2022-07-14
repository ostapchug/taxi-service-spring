package com.example.taxiservicespring.service.model;

import lombok.Data;

@Data
public class Car {
    private long id;
    private String regNumber;
    private long modelId;
    private int categoryId;
    private long locationId;
    private CarStatus status = CarStatus.READY;
}
