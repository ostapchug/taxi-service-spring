package com.example.taxiservicespring.controller.dto;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

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
    private LocalTime waitTime;
    private List<CarDto> cars;
}
