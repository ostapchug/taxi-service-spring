package com.example.taxiservicespring.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class LocationDto {

    @JsonProperty(access = Access.READ_ONLY)
    private long id;
    private String streetName;
    private String streetNumber;
}
