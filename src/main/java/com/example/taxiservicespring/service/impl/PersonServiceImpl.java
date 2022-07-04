package com.example.taxiservicespring.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.taxiservicespring.controller.dto.PersonDto;
import com.example.taxiservicespring.service.PersonService;
import com.example.taxiservicespring.service.mapper.PersonMapper;
import com.example.taxiservicespring.service.model.Person;
import com.example.taxiservicespring.service.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{
	
	private final PersonRepository personRepository;
	
	@Override
	public PersonDto find(long id) {
		
		log.info("get person by id {}", id);
		Person person = personRepository.find(id);
		
		return PersonMapper.INSTANCE.mapPersoDto(person);
	}


	@Override
	public PersonDto find(String phone) {
		
		log.info("get person by phone {}", phone);
		Person person = personRepository.find(phone);
		
		return PersonMapper.INSTANCE.mapPersoDto(person);
	}

	@Override
	public List<PersonDto> getAll() {
		
		log.info("get all persons");
		
		return personRepository.getAll()
				.stream()
				.map(person -> PersonMapper.INSTANCE.mapPersoDto(person))
				.collect(Collectors.toList());
	}

	@Override
	public PersonDto create(PersonDto personDto) {
		
		log.info("create person with phone {}", personDto.getPhone());
		Person person = PersonMapper.INSTANCE.mapPerson(personDto);
		person = personRepository.create(person);
		
		return PersonMapper.INSTANCE.mapPersoDto(person);
	}

	@Override
	public PersonDto update(String phone, PersonDto personDto) {
		
		log.info("update person with phone {}", phone);
		Person person = PersonMapper.INSTANCE.mapPerson(personDto);
		person = personRepository.update(phone, person);
		
		return PersonMapper.INSTANCE.mapPersoDto(person);
	}

	@Override
	public void delete(String phone) {
		log.info("delete person with phone {}", phone);
		personRepository.delete(phone);
		
	}

}
