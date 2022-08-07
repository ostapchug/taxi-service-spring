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

    List<Trip> findAll(int offset, int count, String sorting);

    List<Trip> findAllByPersonId(long personId, int offset, int count, String sorting);

    List<Trip> findAllByDate(LocalDateTime[] dateRange, int offset, int count, String sorting);

    List<Trip> findAllByPersonIdAndDate(long personId, LocalDateTime[] dateRange, int offset, int count, String sorting);

    List<Car> findCarsByTripId(long tripId);

    Trip updateStatus(long tripId, TripStatus status);

    BigDecimal getTotalBill(long personId, TripStatus status);
}
