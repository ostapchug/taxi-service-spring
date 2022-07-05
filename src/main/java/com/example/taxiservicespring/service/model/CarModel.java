package com.example.taxiservicespring.service.model;

import lombok.Data;

@Data
public class CarModel {
    private long id;
    private String brand;
    private String name;
    private String color;
    private int year;
    private int seatCount;
}
