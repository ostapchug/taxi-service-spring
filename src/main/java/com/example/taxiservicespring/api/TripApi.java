package com.example.taxiservicespring.api;

import java.time.LocalDateTime;
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
import com.example.taxiservicespring.service.model.TripStatus;
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

    @ApiOperation("Get all trips")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<TripDto> getAll();

    @ApiImplicitParams({
        @ApiImplicitParam(name = "personId", paramType = "path", required = true, value = "Person id")
        })
    @ApiOperation("Get trips by person id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/person/{personId}")
    List<TripDto> getAllByPersonId(@PathVariable long personId);

    @ApiImplicitParams({
        @ApiImplicitParam(name = "dateRange", paramType = "path", required = true, value = "Date range")
        })
    @ApiOperation("Get trips by date")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/date/{dateRange}")
    List<TripDto> getAllByDate(@PathVariable LocalDateTime[] dateRange);

    @ApiImplicitParams({
        @ApiImplicitParam(name = "personId", paramType = "path", required = true, value = "Person id"),
        @ApiImplicitParam(name = "dateRange", paramType = "path", required = true, value = "Date range")
        })
    @ApiOperation("Get trips by person id and date")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{personId}/{dateRange}")
    List<TripDto> getAllByPersonIdAndDate(@PathVariable long personId, @PathVariable LocalDateTime[] dateRange);

    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Trip id")
    })
    @ApiOperation("Get trip cars by trip id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/car/{id}")
    List<CarDto> getCarsByTripId(@PathVariable long id);

    @ApiOperation("Create trip")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    TripConfirmDto create(@Valid @RequestBody TripCreateDto tripCreateDto);

    @ApiOperation("Confirm trip")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/confirm")
    TripDto confirm(@Valid @RequestBody TripConfirmDto tripConfirmDto);

    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Trip id"),
        @ApiImplicitParam(name = "status", paramType = "path", required = true, value = "Trip status")
        })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/status/{id}/{status}")
    TripDto updateStatus(@PathVariable long id, @PathVariable TripStatus status);
}
