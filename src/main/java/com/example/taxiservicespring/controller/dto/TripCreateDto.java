package com.example.taxiservicespring.controller.dto;

import javax.validation.constraints.Positive;

import com.example.taxiservicespring.controller.validation.Different;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Different({ "originId", "destinationId" })
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

    private boolean multipleCars;
    private boolean ignoreCategory;
}
