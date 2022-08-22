package com.example.taxiservicespring.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.example.taxiservicespring.service.model.TripStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TripDto {
    private long id;
    private long personId;
    private long originId;
    private long destinationId;
    private BigDecimal distance;
    private LocalDateTime date;
    private BigDecimal bill;
    private TripStatus status;
    private Set<CarDto> cars;
}
