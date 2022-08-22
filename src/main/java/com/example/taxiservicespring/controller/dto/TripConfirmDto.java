package com.example.taxiservicespring.controller.dto;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.example.taxiservicespring.controller.validation.Different;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Different({ "originId", "destinationId" })
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

    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal total;
    private LocalTime waitTime;

    @NotNull
    @Size(min = 1)
    private List<CarDto> cars;
}
