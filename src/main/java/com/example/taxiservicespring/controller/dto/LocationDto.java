package com.example.taxiservicespring.controller.dto;

import lombok.Data;

@Data
public class LocationDto {
    private long id;
    private String streetName;
    private String streetNumber;
}
