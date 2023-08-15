package com.example.taxiservicespring.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/trip")
public class TripController {
    private final TripService tripService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/id/{id}")
    public TripDto getById(@PathVariable long id) {
        log.info("request trip with id {}", id);
        return tripService.getById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/car/{tripId}")
    public List<CarDto> getCarsByTripId(@PathVariable long tripId) {
        log.info("request cars by trip id {}", tripId);
        return tripService.getCarsByTripId(tripId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TripDto> getAll() {
        log.info("request list of all trips");
        return tripService.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/person/{personId}")
    public List<TripDto> getAllByPersonId(@PathVariable long personId) {
        log.info("request list of trips filtered by person id {}", personId);
        return tripService.getAllByPersonId(personId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/date/{dateRange}")
    public List<TripDto> getAllByDate(@PathVariable LocalDateTime[] dateRange) {
        log.info("request list of trips filtered by date range {} - {}", dateRange[0], dateRange[1]);
        return tripService.getAllByDate(dateRange);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{personId}/{dateRange}")
    public List<TripDto> getAllByPersonIdAndDate(@PathVariable long personId,
            @PathVariable LocalDateTime[] dateRange) {
        log.info("request list of trips filtered by person id {} and date range {} - {}",
                personId, dateRange[0], dateRange[1]);
        return tripService.getAllByPersonIdAndDate(personId, dateRange);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TripConfirmDto create(@RequestBody TripCreateDto tripCreateDto) {
        log.info("request create new trip");
        return tripService.create(tripCreateDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/confirm")
    public TripDto confirm(@RequestBody TripConfirmDto tripConfirmDto) {
        log.info("request confirm new trip");
        return tripService.confirm(tripConfirmDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/status/{id}/{status}")
    public TripDto updateStatus(@PathVariable long id, @PathVariable TripStatus status) {
        log.info("request update trip status");
        return tripService.updateStatus(id, status);
    }
}
