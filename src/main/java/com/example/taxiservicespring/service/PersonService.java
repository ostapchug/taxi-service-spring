package com.example.taxiservicespring.service;

import java.util.List;

import com.example.taxiservicespring.controller.dto.PersonDto;

public interface PersonService {

    PersonDto getById(long id);

    PersonDto getByPhone(String phone);

    List<PersonDto> getAll();

    PersonDto create(PersonDto personDto);

    PersonDto update(String phone, PersonDto personDto);

    void delete(String phone);
}
