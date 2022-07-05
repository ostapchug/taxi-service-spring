package com.example.taxiservicespring.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class CarModelDto {

    @JsonProperty(access = Access.READ_ONLY)
    private long id;
    private String brand;
    private String name;
    private String color;
    private int year;
}
