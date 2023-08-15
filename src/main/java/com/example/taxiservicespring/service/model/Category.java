package com.example.taxiservicespring.service.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {
    private int id;
    private String name;
    private BigDecimal price;
}
