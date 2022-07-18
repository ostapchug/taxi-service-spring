package com.example.taxiservicespring.controller.dto;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.example.taxiservicespring.controller.validation.Different;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Different({"originId", "destinationId"})
public class TripConfirmDto {
    
    @Positive
    private long personId;
    
    @Positive
    private long originId;
    
    @Positive
    private long destinationId;
    
    @Positive
    private int categoryId;
    
    @Min(message = "{trip.distance}", value = 1)
    private BigDecimal distance;
    
    @Positive
    private BigDecimal price;
    
    @PositiveOrZero
    private BigDecimal discount;
    
    @Positive
    private BigDecimal total;
    
    private LocalTime waitTime;

    @NotNull
    @Size(min = 1)
    private List<CarDto> cars;
}
