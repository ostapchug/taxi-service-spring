package com.example.taxiservicespring.service.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.taxiservicespring.controller.dto.TripDto;
import com.example.taxiservicespring.service.model.Trip;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = CarMapper.class)
public interface TripMapper {

    @Mapping(target="personId", source="person.id")
    @Mapping(target="originId", source="origin.id")
    @Mapping(target="destinationId", source="destination.id")
    TripDto mapTripDto(Trip trip);
}
