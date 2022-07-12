package com.example.taxiservicespring.controller.dto;

import lombok.Data;

@Data
public class CarModelDto {
    private long id;
    private String brand;
    private String name;
    private String color;
    private int year;
}
