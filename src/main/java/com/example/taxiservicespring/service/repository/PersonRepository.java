package com.example.taxiservicespring.service.repository;

import java.util.List;

import com.example.taxiservicespring.service.model.Person;

public interface PersonRepository {

    Person findById(long id);

    Person findByPhone(String phone);

    List<Person> findAll();

    Person create(Person person);

    Person update(String phone, Person person);

    void delete(String phone);
}
