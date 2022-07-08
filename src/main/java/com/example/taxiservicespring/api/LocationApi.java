package com.example.taxiservicespring.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.taxiservicespring.controller.dto.LocationDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Location management API")
@RequestMapping("/api/v1/location")
public interface LocationApi {

    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Location id")
        })
    @ApiOperation("Get location by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/id/{id}")
    LocationDto getById(@PathVariable long id);

    @ApiOperation("Get all locations")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<LocationDto> getAll();
}
