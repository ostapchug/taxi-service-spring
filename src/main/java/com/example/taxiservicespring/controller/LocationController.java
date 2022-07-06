package com.example.taxiservicespring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.taxiservicespring.controller.dto.LocationDto;
import com.example.taxiservicespring.service.LocationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/location/id/{id}")
    public LocationDto getById(@PathVariable long id) {
        log.info("request location with id {}", id);
        return locationService.find(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/location")
    public List<LocationDto> getAll() {
        log.info("request list of all locations");
        return locationService.getAll();
    }
}
