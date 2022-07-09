package com.example.taxiservicespring.service.repository;

import java.math.BigDecimal;
import java.util.List;

import com.example.taxiservicespring.service.model.Location;

public interface LocationRepository {

    Location find(long id);

    List<Location> getAll();

    BigDecimal findDistance(long originId, long destinationId);
}