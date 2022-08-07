package com.example.taxiservicespring.service.repository.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.taxiservicespring.service.model.Car;
import com.example.taxiservicespring.service.model.Trip;
import com.example.taxiservicespring.service.model.TripStatus;
import com.example.taxiservicespring.service.repository.TripRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TripRepositoryImpl implements TripRepository {
    private final List<Trip> trips = new ArrayList<>();
    private final Map<Long, List<Car>> m2mTripCar = new HashMap<>();
    private long uid = 0;

    @Override
    public Trip find(long id) {
        log.info("find trip by id {}", id);
        return trips.stream()
                .filter(trip -> trip.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Trip is not found!"));
    }

    @Override
    public Trip create(Trip trip, List<Car> cars) {
        log.info("create trip");
        trip.setId(++uid);
        trip.setDate(LocalDateTime.now());
        m2mTripCar.put(trip.getId(), cars);
        trips.add(trip);
        return trip;
    }

    @Override
    public List<Trip> findAll(int offset, int count, String sorting) {
        log.info("find all trips");
        List<Trip> result = sort(sorting);
        return getSubList(offset, count, result);
    }

    @Override
    public List<Trip> findAllByPersonId(long personId, int offset, int count, String sorting) {
        log.info("find trips filtered by person id {}", personId);
        List<Trip> result = sort(sorting).stream()
                .filter(trip -> trip.getPersonId() == personId)
                .collect(Collectors.toList());
        return getSubList(offset, count, result);
    }

    @Override
    public List<Trip> findAllByDate(LocalDateTime[] dateRange, int offset, int count, String sorting) {
        log.info("find trips filtered by date {}", Arrays.toString(dateRange));
        List<Trip> result = sort(sorting).stream()
                .filter(trip -> trip.getDate().isAfter(dateRange[0]) && trip.getDate().isBefore(dateRange[1]))
                .collect(Collectors.toList());
        return getSubList(offset, count, result);
    }

    @Override
    public List<Trip> findAllByPersonIdAndDate(long personId, LocalDateTime[] dateRange, int offset, int count,
            String sorting) {
        log.info("find trips filtered by person id {} and date {}", personId, Arrays.toString(dateRange));
        List<Trip> result = sort(sorting).stream()
                .filter(t -> t.getPersonId() == personId)
                .filter(trip -> trip.getDate().isAfter(dateRange[0]) && trip.getDate().isBefore(dateRange[1]))
                .collect(Collectors.toList());
        return getSubList(offset, count, result);
    }

    @Override
    public List<Car> findCarsByTripId(long tripId) {
        log.info("find cars by trip id {}", tripId);
        return m2mTripCar.getOrDefault(tripId, new ArrayList<>());
    }

    @Override
    public Trip updateStatus(long tripId, TripStatus status) {
        log.info("update status to {} for trip with id {}", status, tripId);
        Trip trip = find(tripId);
        trip.setStatus(status);
        return trip;
    }

    @Override
    public BigDecimal getTotalBill(long personId, TripStatus status) {
        log.info("find total bill for person with id {}", personId);
        return trips.stream()
                .filter(t -> t.getPersonId() == personId && t.getStatus().equals(status))
                .map(t -> t.getBill())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<Trip> sort(String sorting) {
        List<Trip> result = new ArrayList<>(trips);

        switch (sorting) {
        case "date-asc":
            result.sort((t1, t2) -> t1.getDate().compareTo(t2.getDate()));
            break;
        case "bill-asc":
            result.sort((t1, t2) -> t1.getBill().compareTo(t2.getBill()));
            break;
        case "bill-desc":
            result.sort((t1, t2) -> t2.getBill().compareTo(t1.getBill()));
            break;
        default:
            result.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));
            break;
        }
        return result;
    }

    private List<Trip> getSubList(int offset, int count, List<Trip> list) {
        List<Trip> result = null;
        int size = list.size();
        int fromIndex = offset >= size && count <= size ? size - count : offset;
        int toIndex = count + fromIndex > size || count > size ? size : count + fromIndex;

        if (fromIndex >= size || fromIndex < 0 || toIndex > size || toIndex < 0) {
            result = new ArrayList<>();
        } else {
            result = list.subList(fromIndex, toIndex);
        }
        return result;
    }
}
