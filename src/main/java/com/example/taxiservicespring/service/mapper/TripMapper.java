package com.example.taxiservicespring.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.taxiservicespring.controller.dto.TripDto;
import com.example.taxiservicespring.service.model.Trip;

@Mapper
public interface TripMapper {
    TripMapper INSTANCE = Mappers.getMapper(TripMapper.class);

    TripDto mapTripDto(Trip trip);
}
