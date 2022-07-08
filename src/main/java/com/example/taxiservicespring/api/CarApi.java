package com.example.taxiservicespring.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.CarModelDto;
import com.example.taxiservicespring.controller.dto.CategoryDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Car management API")
@RequestMapping("/api/v1/car")
public interface CarApi {

    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Car id")
        })
    @ApiOperation("Get car by id")
    @GetMapping(value = "/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    CarDto getById(@PathVariable long id);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Car category id")
            })
    @ApiOperation("Get car category by id")
    @GetMapping(value = "/category/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    CategoryDto getCategoryById(@PathVariable int id);

    @ApiOperation("Get all car categories")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/category")
    List<CategoryDto> getAllCategories();

    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Car model id")
    })
    @ApiOperation("Get car model by id")
    @GetMapping(value = "/car-model/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    CarModelDto getCarModelById(@PathVariable long id);
}
