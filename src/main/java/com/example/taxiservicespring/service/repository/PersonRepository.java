package com.example.taxiservicespring.service.repository;

import java.util.List;

import com.example.taxiservicespring.service.model.Person;

public interface PersonRepository {

    Person find(long id);

    Person find(String phone);

    List<Person> getAll();

    Person create(Person person);

    Person update(String phone, Person person);

    void delete(String phone);
}
