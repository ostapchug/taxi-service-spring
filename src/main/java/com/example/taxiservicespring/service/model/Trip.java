package com.example.taxiservicespring.service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Trip {
    private long id;
    private long personId;
    private long originId;
    private long destinationId;
    private BigDecimal distance;
    private LocalDateTime date;
    private BigDecimal bill;
    @Builder.Default
    private TripStatus status = TripStatus.NEW;
}
