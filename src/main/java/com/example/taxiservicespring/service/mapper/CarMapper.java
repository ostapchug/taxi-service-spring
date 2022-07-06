package com.example.taxiservicespring.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.service.model.Car;
import com.example.taxiservicespring.service.model.CarStatus;

@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(source = "statusId", target = "status", qualifiedByName = "idToName")
    CarDto mapCarDto(Car car);

    @Named("idToName")
    static String idToName(int id) {
        return CarStatus.getName(id);
    }
}
