package com.example.taxiservicespring.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarModel {
    private long id;
    private String brand;
    private String name;
    private String color;
    private int year;
    private int seatCount;
}
