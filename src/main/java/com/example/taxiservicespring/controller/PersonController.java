package com.example.taxiservicespring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.taxiservicespring.controller.dto.PersonDto;
import com.example.taxiservicespring.service.PersonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PersonController {
	
	private final PersonService personService;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/person/id/{id}")
	public PersonDto find(@PathVariable long id) {
		
		log.info("request person with id {}", id);
		
		return personService.find(id);		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/person/phone/{phone}")
	public PersonDto find(@PathVariable String phone) {
		
		log.info("request person with phone {}", phone);
		
		return personService.find(phone);		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/person")
	public List<PersonDto> getAll() {
		
		log.info("request list of all persons");
		
		return personService.getAll();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/person")
	public PersonDto create(@RequestBody PersonDto personDto) {
		
		log.info("request create new person");
		
		return personService.create(personDto);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = "/person/{phone}")
	public PersonDto update(@PathVariable String phone, @RequestBody PersonDto personDto) {
		
		log.info("request update person with phone {}", phone);
		
		return personService.update(phone, personDto);
	}
	
	@DeleteMapping(value = "/person/{phone}")
	public ResponseEntity<Void> delete(@PathVariable String phone) {
		
		log.info("request delete person with phone {}", phone);
		personService.delete(phone);
		
		return ResponseEntity.noContent().build();
	}
	
	

}
