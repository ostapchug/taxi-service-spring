package com.example.taxiservicespring.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.TripConfirmDto;
import com.example.taxiservicespring.controller.dto.TripCreateDto;
import com.example.taxiservicespring.controller.dto.TripDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Trip management API")
@RequestMapping("/api/v1/trip")
public interface TripApi {

    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Trip id")
        })
    @ApiOperation("Get trip by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/id/{id}")
    TripDto getById(@PathVariable long id);

    @ApiImplicitParams({
        @ApiImplicitParam(name = "tripId", paramType = "path", required = true, value = "Trip id")
    })
    @ApiOperation("Get trip cars by trip id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/car/{tripId}")
    List<CarDto> getCarsByTripId(@PathVariable long tripId);

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", paramType = "path", required = true, value = "Page value"),
        @ApiImplicitParam(name = "count", paramType = "path", required = true, value = "Count of trips per page"),
        @ApiImplicitParam(name = "sorting", paramType = "path", required = true, value = "Sorting method")
        })
    @ApiOperation("Get all trips, supports pagination and sorting")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{page}/{count}/{sorting}")
    List<TripDto> getAll(@PathVariable int page, @PathVariable int count, @PathVariable String sorting);

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", paramType = "path", required = true, value = "Page value"),
        @ApiImplicitParam(name = "count", paramType = "path", required = true, value = "Count of trips per page"),
        @ApiImplicitParam(name = "sorting", paramType = "path", required = true, value = "Sorting method"),
        @ApiImplicitParam(name = "personId", paramType = "path", required = true, value = "Person id")
        })
    @ApiOperation("Get trips by person id, supports pagination and sorting")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{page}/{count}/{sorting}/person-id/{personId}")
    List<TripDto> getAllByPersonId(@PathVariable int page, @PathVariable int count, @PathVariable String sorting,
            @PathVariable int personId);
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", paramType = "path", required = true, value = "Page value"),
        @ApiImplicitParam(name = "count", paramType = "path", required = true, value = "Count of trips per page"),
        @ApiImplicitParam(name = "sorting", paramType = "path", required = true, value = "Sorting method"),
        @ApiImplicitParam(name = "dateRange", paramType = "path", required = true, value = "Date range")
        })
    @ApiOperation("Get trips by date, supports pagination and sorting")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{page}/{count}/{sorting}/date/{dateRange}")
    List<TripDto> getAllByDate(@PathVariable int page, @PathVariable int count, @PathVariable String sorting,
            @PathVariable String dateRange);
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", paramType = "path", required = true, value = "Page value"),
        @ApiImplicitParam(name = "count", paramType = "path", required = true, value = "Count of trips per page"),
        @ApiImplicitParam(name = "sorting", paramType = "path", required = true, value = "Sorting method"),
        @ApiImplicitParam(name = "personId", paramType = "path", required = true, value = "Person id"),
        @ApiImplicitParam(name = "dateRange", paramType = "path", required = true, value = "Date range")
        })
    @ApiOperation("Get trips by person id and date, supports pagination and sorting")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{page}/{count}/{sorting}/{personId}/{dateRange}")
    List<TripDto> getAllByPersonIdAndDate(@PathVariable int page, @PathVariable int count,
            @PathVariable String sorting, @PathVariable int personId, @PathVariable String dateRange);
    
    @ApiOperation("Create trip")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    TripConfirmDto create(@Valid @RequestBody TripCreateDto tripCreateDto);
    
    @ApiOperation("Confirm trip")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/confirm")
    TripDto confirm(@Valid @RequestBody TripConfirmDto tripConfirmDto);
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "tripId", paramType = "path", required = true, value = "Trip id"),
        @ApiImplicitParam(name = "status", paramType = "path", required = true, value = "Trip status")
        })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/status/{tripId}/{status}")
    TripDto updateStatus(@PathVariable long tripId, @PathVariable String status);
}
