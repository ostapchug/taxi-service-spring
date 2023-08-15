package com.example.taxiservicespring.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.taxiservicespring.controller.dto.LocationDto;
import com.example.taxiservicespring.service.LocationService;
import com.example.taxiservicespring.service.mapper.LocationMapper;
import com.example.taxiservicespring.service.model.Location;
import com.example.taxiservicespring.service.repository.LocationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Override
    public LocationDto getById(long id) {
        log.info("get location by id {}", id);
        Location location = locationRepository.findById(id);
        return LocationMapper.INSTANCE.mapLocationDto(location);
    }

    @Override
    public List<LocationDto> getAll() {
        log.info("get all locations");
        return locationRepository.findAll()
                .stream()
                .map(location -> LocationMapper.INSTANCE.mapLocationDto(location))
                .collect(Collectors.toList());
    }
}
