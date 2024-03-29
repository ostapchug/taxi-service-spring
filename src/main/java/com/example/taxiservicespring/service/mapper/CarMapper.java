package com.example.taxiservicespring.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.service.model.Car;

@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(source = "model.id", target = "modelId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "location.id", target = "locationId")
    CarDto mapCarDto(Car car);
}
