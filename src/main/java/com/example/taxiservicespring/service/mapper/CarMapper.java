package com.example.taxiservicespring.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.service.model.Car;

@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);
    
    @Mapping(target="modelId", source="model.id")
    @Mapping(target="categoryId", source="category.id")
    @Mapping(target="locationId", source="location.id")
    CarDto mapCarDto(Car car);  
}
