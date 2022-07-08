package com.example.taxiservicespring.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.taxiservicespring.api.PersonApi;
import com.example.taxiservicespring.controller.dto.PersonDto;
import com.example.taxiservicespring.service.PersonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PersonController implements PersonApi{
    private final PersonService personService;
    
    public PersonDto getById(long id) {
        log.info("request person with id {}", id);
        return personService.find(id);
    }

    public PersonDto getByPhone(String phone) {
        log.info("request person with phone {}", phone);
        return personService.find(phone);
    }

    public List<PersonDto> getAll() {
        log.info("request list of all persons");
        return personService.getAll();
    }

    public PersonDto create(PersonDto personDto) {
        log.info("request create new person");
        return personService.create(personDto);
    }

    public PersonDto update(String phone, PersonDto personDto) {
        log.info("request update person with phone {}", phone);
        PersonDto personDtoDb = personService.update(phone, personDto);
        return personDtoDb;
    }

    public ResponseEntity<Void> delete(String phone) {
        log.info("request delete person with phone {}", phone);
        personService.delete(phone);
        return ResponseEntity.noContent().build();
    }
}
