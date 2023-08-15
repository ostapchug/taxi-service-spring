package com.example.taxiservicespring.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.taxiservicespring.controller.dto.PersonDto;
import com.example.taxiservicespring.service.model.Person;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "password", ignore = true)
    PersonDto mapPersonDto(Person person);

    @Mapping(target = "role", ignore = true)
    Person mapPerson(PersonDto personDto);
}
