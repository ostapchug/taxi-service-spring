package com.example.taxiservicespring.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.taxiservicespring.controller.dto.PersonDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Person management API")
@RequestMapping("/api/v1/person")
public interface PersonApi {

    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Person id")
        })
    @ApiOperation("Get person by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/id/{id}")
    PersonDto getById(@PathVariable long id);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", paramType = "path", required = true, value = "Person phone")
            })
    @ApiOperation("Get person by phone")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/phone/{phone}")
    PersonDto getByPhone(@PathVariable String phone);

    @ApiOperation("Get all persons")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<PersonDto> getAll();

    @ApiOperation("Create person")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    PersonDto create(@Valid @RequestBody PersonDto personDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", paramType = "path", required = true, value = "Person phone")
            })
    @ApiOperation("Update person")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{phone}")
    PersonDto update(@PathVariable String phone, @Valid @RequestBody PersonDto personDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", paramType = "path", required = true, value = "Person phone")
            })
    @DeleteMapping(value = "/{phone}")
    ResponseEntity<Void> delete(@PathVariable String phone);
}
