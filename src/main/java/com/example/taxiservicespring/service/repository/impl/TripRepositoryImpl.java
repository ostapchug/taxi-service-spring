package com.example.taxiservicespring.service.repository.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.taxiservicespring.service.model.Car;
import com.example.taxiservicespring.service.model.Trip;
import com.example.taxiservicespring.service.repository.TripRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TripRepositoryImpl implements TripRepository {
    private final List<Trip> trips = new ArrayList<>();
    private final Map<Long, List<Car>> m2mTripCar = new HashMap<>();
    private AtomicLong uid = new AtomicLong(0);

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
        trip.setId(uid.incrementAndGet());
        trip.setDate(Timestamp.from(Instant.now()));
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
    public List<Trip> findAllByDate(Timestamp[] dateRange, int offset, int count, String sorting) {
        log.info("find trips filtered by date {}", Arrays.toString(dateRange));
        List<Trip> result = sort(sorting).stream()
                .filter(trip -> trip.getDate().after(dateRange[0]) && trip.getDate().before(dateRange[1]))
                .collect(Collectors.toList());
        return getSubList(offset, count, result);
    }

    @Override
    public List<Trip> findAllByPersonIdAndDate(long personId, Timestamp[] dateRange, int offset, int count,
            String sorting) {
        log.info("find trips filtered by person id {} and date {}", personId, Arrays.toString(dateRange));
        List<Trip> result = sort(sorting).stream()
                .filter(t -> t.getPersonId() == personId)
                .filter(trip -> trip.getDate().after(dateRange[0]) && trip.getDate().before(dateRange[1]))
                .collect(Collectors.toList());
        return getSubList(offset, count, result);
    }

    @Override
    public List<Car> findCarsByTripId(long tripId) {
        log.info("find cars by trip id {}", tripId);
        return m2mTripCar.getOrDefault(tripId, new ArrayList<>());
    }

    @Override
    public long getCount() {
        log.info("find count of all trips");
        return trips.size();
    }

    @Override
    public long getCountByPersonId(long personId) {
        log.info("find count of trips filtered by person id {}", personId);
        return trips.stream()
                .filter(trip -> trip.getPersonId() == personId)
                .count();
    }

    @Override
    public long getCountByDate(Timestamp[] dateRange) {
        log.info("find count of trips filtered by date {}", Arrays.toString(dateRange));
        return trips.stream()
                .filter(trip -> trip.getDate().after(dateRange[0]) && trip.getDate().before(dateRange[1]))
                .count();
    }

    @Override
    public long getCountByPersonIdAndDate(long personId, Timestamp[] dateRange) {
        log.info("find count of trips filtered by person id {} and date {}", personId, Arrays.toString(dateRange));
        return trips.stream()
                .filter(t -> t.getPersonId() == personId)
                .filter(t -> t.getDate().after(dateRange[0]) && t.getDate().before(dateRange[1]))
                .count();
    }

    @Override
    public Trip updateStatus(long tripId, int statusId) {
        log.info("update status id to {} for trip with id {}", statusId, tripId);
        Trip trip = find(tripId);
        trip.setStatusId(statusId);
        return trip;
    }

    @Override
    public BigDecimal getTotalBill(long personId) {
        log.info("find total bill for person with id {}", personId);
        return trips.stream()
                .filter(t -> t.getPersonId() == personId)
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
