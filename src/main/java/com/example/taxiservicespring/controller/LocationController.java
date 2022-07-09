package com.example.taxiservicespring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.example.taxiservicespring.api.LocationApi;
import com.example.taxiservicespring.controller.dto.LocationDto;
import com.example.taxiservicespring.service.LocationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LocationController implements LocationApi {
    private final LocationService locationService;

    @Override
    public LocationDto getById(long id) {
        log.info("request location with id {}", id);
        return locationService.find(id);
    }

    @Override
    public List<LocationDto> getAll() {
        log.info("request list of all locations");
        return locationService.getAll();
    }
}
