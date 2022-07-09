package com.example.taxiservicespring.controller.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.example.taxiservicespring.controller.validation.Different;
import com.example.taxiservicespring.service.model.Car;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Different({"originId", "destinationId"})
public class TripConfirmDto {
    
    @PositiveOrZero
    private long personId;
    
    @PositiveOrZero
    private long originId;
    
    @PositiveOrZero
    private long destinationId;
    
    @PositiveOrZero
    private int categoryId;
    
    @Min(message = "{trip.distance}", value = 1)
    private BigDecimal distance;
    
    @Positive
    private BigDecimal price;
    
    @PositiveOrZero
    private BigDecimal discount;
    
    @Positive
    private BigDecimal total;
    
    @PositiveOrZero
    private int waitTime;
    
    @NotNull
    @Size(min = 1)
    private List<Car> cars;
}