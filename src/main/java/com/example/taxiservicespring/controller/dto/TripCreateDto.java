package com.example.taxiservicespring.controller.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.example.taxiservicespring.controller.validation.Different;

import lombok.Data;

@Data
@Different({"originId", "destinationId"})
public class TripCreateDto {
    
    @PositiveOrZero
    private long personId;
    
    @PositiveOrZero
    private long originId;
    
    @PositiveOrZero
    private long destinationId;
    
    @PositiveOrZero
    private int categoryId;
    
    @Positive(message = "{car.capacity}")
    private int capacity;
    
    boolean multipleCars;
    
    boolean ignoreCategory;
}
