package com.example.taxiservicespring.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Car {
    private long id;
    private String regNumber;
    private long modelId;
    private int categoryId;
    private long locationId;
    @Builder.Default
    private CarStatus status = CarStatus.READY;
}
