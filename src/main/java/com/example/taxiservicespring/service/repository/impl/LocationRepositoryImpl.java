package com.example.taxiservicespring.service.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.taxiservicespring.service.exception.EntityNotFoundException;
import com.example.taxiservicespring.service.model.Location;
import com.example.taxiservicespring.service.repository.LocationRepository;
import com.example.taxiservicespring.util.DataGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LocationRepositoryImpl implements LocationRepository {
    private final List<Location> locations = DataGenerator.createLocations();

    @Override
    public Location find(long id) {
        log.info("find location by id {}", id);
        return locations.stream()
                .filter(location -> location.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Location is not found!"));
    }

    @Override
    public List<Location> getAll() {
        log.info("find all locations");
        return new ArrayList<>(locations);
    }
}
