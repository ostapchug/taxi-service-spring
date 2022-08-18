package com.example.taxiservicespring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.TripConfirmDto;
import com.example.taxiservicespring.controller.dto.TripCreateDto;
import com.example.taxiservicespring.controller.dto.TripDto;
import com.example.taxiservicespring.service.TripService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TripController {
    private final TripService tripService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/trip/id/{id}")
    public TripDto getById(@PathVariable long id) {
        log.info("request trip with id {}", id);
        return tripService.find(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/trip/car/{tripId}")
    public List<CarDto> getCarsByTripId(@PathVariable long tripId) {
        log.info("request cars by trip id {}", tripId);
        return tripService.findCarsByTripId(tripId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/trip")
    public List<TripDto> getAll() {
        log.info("request list of all trips");
        return tripService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/trip/person-id/{personId}")
    public List<TripDto> getAllByPersonId(@PathVariable int personId) {
        log.info("request list of trips filtered by person id {}", personId);
        return tripService.getAllByPersonId(personId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/trip/date/{dateRange}")
    public List<TripDto> getAllByDate(@PathVariable String dateRange) {
        log.info("request list of trips filtered by date {}", dateRange);
        return tripService.getAllByDate(dateRange);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/trip/{personId}/{dateRange}")
    public List<TripDto> getAllByPersonIdAndDate(@PathVariable int personId, @PathVariable String dateRange) {
        log.info("request list of trips filtered by person id {} and date {}", personId, dateRange);
        return tripService.getAllByPersonIdAndDate(personId, dateRange);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/trip")
    public TripConfirmDto create(@RequestBody TripCreateDto tripCreateDto) {
        log.info("request create new trip");
        return tripService.create(tripCreateDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/trip/confirm")
    public TripDto confirm(@RequestBody TripConfirmDto tripConfirmDto) {
        log.info("request confirm new trip");
        return tripService.confirm(tripConfirmDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/trip/status/{tripId}/{status}")
    public TripDto updateStatus(@PathVariable long tripId, @PathVariable String status) {
        log.info("request update trip status");
        return tripService.updateStatus(tripId, status);
    }
}
