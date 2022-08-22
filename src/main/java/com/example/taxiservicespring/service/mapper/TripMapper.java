package com.example.taxiservicespring.service.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.taxiservicespring.controller.dto.TripDto;
import com.example.taxiservicespring.service.model.Trip;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = CarMapper.class)
public interface TripMapper {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "origin.id", target = "originId")
    @Mapping(source = "destination.id", target = "destinationId")
    TripDto mapTripDto(Trip trip);
}
