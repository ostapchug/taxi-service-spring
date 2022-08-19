package com.example.taxiservicespring.service.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.taxiservicespring.service.exception.EntityNotFoundException;
import com.example.taxiservicespring.service.model.Person;
import com.example.taxiservicespring.service.repository.PersonRepository;
import com.example.taxiservicespring.util.DataGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PersonRepositoryImpl implements PersonRepository {
    private final List<Person> persons = DataGenerator.createPersons();
    private long uid = 1L;

    @Override
    public Person find(long id) {
        log.info("find person by id {}", id);
        return persons.stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Person is not found!"));
    }

    @Override
    public Person find(String phone) {
        log.info("find person by phone {}", phone);
        return persons.stream()
                .filter(person -> person.getPhone().equals(phone))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Person is not found!"));
    }

    @Override
    public List<Person> getAll() {
        log.info("find all persons");
        return new ArrayList<>(persons);
    }

    @Override
    public Person create(Person person) {
        log.info("create person");
        person.setId(++uid);
        persons.add(person);
        return person;
    }

    @Override
    public Person update(String phone, Person person) {
        log.info("update person with phone {}", phone);
        Person oldPerson = find(phone);
        person.setId(oldPerson.getId());
        person.setPhone(oldPerson.getPhone());
        person.setPassword(oldPerson.getPassword());
        persons.remove(oldPerson);
        persons.add(person);
        return person;
    }

    @Override
    public void delete(String phone) {
        log.info("delete person with phone {}", phone);
        persons.removeIf(p -> p.getPhone().equals(phone));
    }
}
