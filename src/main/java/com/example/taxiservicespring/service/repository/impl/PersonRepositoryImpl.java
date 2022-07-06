package com.example.taxiservicespring.service.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.example.taxiservicespring.service.model.Person;
import com.example.taxiservicespring.service.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PersonRepositoryImpl implements PersonRepository {
    private final List<Person> persons = new ArrayList<>();
    private AtomicLong uid = new AtomicLong(0);

    PersonRepositoryImpl() {
        Person person = new Person();
        person.setId(0L);
        person.setPhone("0123456789");
        person.setPassword("Admin#0");
        person.setName("John");
        person.setSurname("Doe");
        person.setRoleId(1);
        persons.add(person);
    }

    @Override
    public Person find(long id) {
        log.info("find person by id {}", id);
        return persons.stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Person is not found!"));
    }

    @Override
    public Person find(String phone) {
        log.info("find person by phone {}", phone);
        return persons.stream()
                .filter(person -> person.getPhone().equals(phone))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Person is not found!"));
    }

    @Override
    public List<Person> getAll() {
        log.info("find all persons");
        return new ArrayList<>(persons);
    }

    @Override
    public Person create(Person person) {
        log.info("create person");
        person.setId(uid.incrementAndGet());
        persons.add(person);
        return person;
    }

    @Override
    public Person update(String phone, Person person) {
        log.info("update person with phone {}", phone);
        Person oldPerson = find(phone);
        person.setId(oldPerson.getId());
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
