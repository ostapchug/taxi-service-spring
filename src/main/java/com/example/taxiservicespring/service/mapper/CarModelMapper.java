package com.example.taxiservicespring.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.taxiservicespring.controller.dto.CarModelDto;
import com.example.taxiservicespring.service.model.CarModel;

@Mapper
public interface CarModelMapper {
	
	CarModelMapper INSTANCE = Mappers.getMapper(CarModelMapper.class);
	
	CarModelDto mapCarModelDto(CarModel carModel);

}
