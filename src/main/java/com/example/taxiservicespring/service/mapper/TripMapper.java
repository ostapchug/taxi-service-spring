package com.example.taxiservicespring.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.example.taxiservicespring.controller.dto.TripDto;
import com.example.taxiservicespring.service.model.Trip;
import com.example.taxiservicespring.service.model.TripStatus;

@Mapper
public interface TripMapper {
    TripMapper INSTANCE = Mappers.getMapper(TripMapper.class);

    @Mapping(source = "statusId", target = "status", qualifiedByName = "idToName")
    TripDto mapTripDto(Trip trip);

    @Named("idToName")
    static String idToName(int id) {
	return TripStatus.getName(id);
    }
}
