package com.example.taxiservicespring.service;

import java.util.List;

import com.example.taxiservicespring.controller.dto.LocationDto;

public interface LocationService {

    LocationDto getById(long id);

    List<LocationDto> getAll();
}
