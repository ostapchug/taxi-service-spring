package com.example.taxiservicespring.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.example.taxiservicespring.controller.dto.PersonDto;
import com.example.taxiservicespring.service.model.Person;
import com.example.taxiservicespring.service.model.Role;

@Mapper
public interface PersonMapper {
	
	PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
	
	@Mapping(target = "password", ignore = true)
	@Mapping(source = "roleId", target = "role", qualifiedByName = "idToName")
	PersonDto mapPersoDto(Person person);
	
	@Mapping(target = "roleId", ignore = true)
	Person mapPerson(PersonDto personDto);
	
	@Named("idToName")
	static String idToName(int id) {
		return Role.getName(id);
	}

}
