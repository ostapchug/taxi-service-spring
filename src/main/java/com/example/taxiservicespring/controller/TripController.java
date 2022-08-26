package com.example.taxiservicespring.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.example.taxiservicespring.api.TripApi;
import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.TripConfirmDto;
import com.example.taxiservicespring.controller.dto.TripCreateDto;
import com.example.taxiservicespring.controller.dto.TripDto;
import com.example.taxiservicespring.service.TripService;
import com.example.taxiservicespring.service.model.TripStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TripController implements TripApi {
    private final TripService tripService;

    @Override
    public TripDto getById(long id) {
        log.info("request trip with id {}", id);
        return tripService.getById(id);
    }

    @Override
    public List<TripDto> getAll() {
        log.info("request list of all trips");
        return tripService.getAll();
    }

    @Override
    public List<TripDto> getAllByPersonId(long personId) {
        log.info("request list of trips filtered by person id {}", personId);
        return tripService.getAllByPersonId(personId);
    }

    @Override
    public List<TripDto> getAllByDate(LocalDateTime[] dateRange) {
        log.info("request list of trips filtered by date range {} - {}", dateRange[0], dateRange[1]);
        return tripService.getAllByDate(dateRange);
    }

    @Override
    public List<TripDto> getAllByPersonIdAndDate(long personId, LocalDateTime[] dateRange) {
        log.info("request list of trips filtered by person id {} and date range {} - {}",
                personId, dateRange[0], dateRange[1]);
        return tripService.getAllByPersonIdAndDate(personId, dateRange);
    }

    @Override
    public List<CarDto> getCarsByTripId(long id) {
        log.info("request cars by trip id {}", id);
        return tripService.getCarsByTripId(id);
    }

    @Override
    public TripConfirmDto create(TripCreateDto tripCreateDto) {
        log.info("request create new trip");
        return tripService.create(tripCreateDto);
    }

    @Override
    public TripDto confirm(TripConfirmDto tripConfirmDto) {
        log.info("request confirm new trip");
        return tripService.confirm(tripConfirmDto);
    }

    @Override
    public TripDto updateStatus(long id, TripStatus status) {
        log.info("request update trip status");
        return tripService.updateStatus(id, status);
    }
}
