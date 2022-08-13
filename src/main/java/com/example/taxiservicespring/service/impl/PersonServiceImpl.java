package com.example.taxiservicespring.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.taxiservicespring.controller.dto.PersonDto;
import com.example.taxiservicespring.service.PersonService;
import com.example.taxiservicespring.service.exception.EntityNotFoundException;
import com.example.taxiservicespring.service.mapper.PersonMapper;
import com.example.taxiservicespring.service.model.Person;
import com.example.taxiservicespring.service.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    public PersonDto find(long id) {
        log.info("get person by id {}", id);
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person is not found!"));
        return PersonMapper.INSTANCE.mapPersonDto(person);
    }

    @Override
    public PersonDto find(String phone) {
        log.info("get person by phone {}", phone);
        Person person = personRepository.findByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Person is not found!"));
        return PersonMapper.INSTANCE.mapPersonDto(person);
    }

    @Override
    public List<PersonDto> getAll() {
        log.info("get all persons");
        return personRepository.findAll()
                .stream()
                .map(person -> PersonMapper.INSTANCE.mapPersonDto(person))
                .collect(Collectors.toList());
    }

    @Override
    public PersonDto create(PersonDto personDto) {
        log.info("create person with phone {}", personDto.getPhone());
        Person person = PersonMapper.INSTANCE.mapPerson(personDto);
        person = personRepository.save(person);
        return PersonMapper.INSTANCE.mapPersonDto(person);
    }

    @Override
    public PersonDto update(String phone, PersonDto personDto) {
        log.info("update person with phone {}", phone);
        Person person = PersonMapper.INSTANCE.mapPerson(personDto);
        Person dbPerson = personRepository.findByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Person is not found!"));
        dbPerson.setName(person.getName());
        dbPerson.setSurname(person.getSurname());
        person = personRepository.save(dbPerson);
        return PersonMapper.INSTANCE.mapPersonDto(person);
    }

    @Override
    public void delete(String phone) {
        log.info("delete person with phone {}", phone);
        Person person = personRepository.findByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Person is not found!"));
        personRepository.delete(person);
    }
}
