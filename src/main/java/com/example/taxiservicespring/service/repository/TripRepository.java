package com.example.taxiservicespring.service.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.taxiservicespring.service.model.Car;
import com.example.taxiservicespring.service.model.Trip;
import com.example.taxiservicespring.service.model.TripStatus;

public interface TripRepository {

    Trip find(long id);

    Trip create(Trip trip, List<Car> cars);

    List<Trip> findAll();

    List<Trip> findAllByPersonId(long personId);

    List<Trip> findAllByDate(LocalDateTime[] dateRange);

    List<Trip> findAllByPersonIdAndDate(long personId, LocalDateTime[] dateRange);

    List<Car> findCarsByTripId(long tripId);

    Trip updateStatus(long tripId, TripStatus status);

    BigDecimal getTotalBill(long personId, TripStatus status);
}
