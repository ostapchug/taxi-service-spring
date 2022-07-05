package com.example.taxiservicespring.service.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
    private Timestamp date;
    private BigDecimal bill;
    private int statusId;
}
