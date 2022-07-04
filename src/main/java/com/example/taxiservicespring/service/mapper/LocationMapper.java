package com.example.taxiservicespring.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.taxiservicespring.controller.dto.LocationDto;
import com.example.taxiservicespring.service.model.Location;

@Mapper
public interface LocationMapper {
	
	LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);
	
	LocationDto mapLocationDto(Location location);

}
