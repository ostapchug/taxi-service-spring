package com.example.taxiservicespring.api;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

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
        @ApiImplicitParam(name = "page", paramType = "query", value = "Page value"),
        @ApiImplicitParam(name = "size", paramType = "query", value = "Count of trips per page"),
        @ApiImplicitParam(name = "sort", paramType = "query", value = "Sorting, example: 'date,desc'")
        })
    @ApiOperation("Get all trips, supports pagination and sorting")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    Page<TripDto> getAll(Pageable pageable);
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "personId", paramType = "path", required = true, value = "Person id"),
        @ApiImplicitParam(name = "page", paramType = "query", value = "Page value"),
        @ApiImplicitParam(name = "count", paramType = "query", value = "Count of trips per page"),
        @ApiImplicitParam(name = "sorting", paramType = "query", value = "Sorting, example: 'date,desc'")
        })
    @ApiOperation("Get trips by person id, supports pagination and sorting")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/person-id/{personId}")
    Page<TripDto> getAllByPersonId(@PathVariable long personId, Pageable pageable);
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dateRange", paramType = "path", required = true, value = "Date range"),
        @ApiImplicitParam(name = "page", paramType = "query", value = "Page value"),
        @ApiImplicitParam(name = "count", paramType = "query", value = "Count of trips per page"),
        @ApiImplicitParam(name = "sorting", paramType = "query", value = "Sorting, example: 'date,desc'")
        })
    @ApiOperation("Get trips by date, supports pagination and sorting")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/date/{dateRange}")
    Page<TripDto> getAllByDate(@PathVariable String dateRange, Pageable pageable);
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "personId", paramType = "path", required = true, value = "Person id"),
        @ApiImplicitParam(name = "dateRange", paramType = "path", required = true, value = "Date range"),
        @ApiImplicitParam(name = "page", paramType = "query", value = "Page value"),
        @ApiImplicitParam(name = "count", paramType = "query", value = "Count of trips per page"),
        @ApiImplicitParam(name = "sorting", paramType = "query", value = "Sorting, example: 'date,desc'")
        })
    @ApiOperation("Get trips by person id and date, supports pagination and sorting")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{personId}/{dateRange}")
    Page<TripDto> getAllByPersonIdAndDate(@PathVariable long personId, @PathVariable String dateRange, Pageable pageable);
    
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
