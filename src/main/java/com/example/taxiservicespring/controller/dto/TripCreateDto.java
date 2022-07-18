package com.example.taxiservicespring.controller.dto;

import javax.validation.constraints.Positive;
import com.example.taxiservicespring.controller.validation.Different;

import lombok.Data;

@Data
@Different({"originId", "destinationId"})
public class TripCreateDto {
    
    @Positive
    private long personId;
    
    @Positive
    private long originId;
    
    @Positive
    private long destinationId;
    
    @Positive
    private int categoryId;
    
    @Positive(message = "{car.capacity}")
    private int capacity;
    
    boolean multipleCars;
    boolean ignoreCategory;
}
