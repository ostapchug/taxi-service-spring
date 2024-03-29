package com.example.taxiservicespring.service.impl;

import static com.example.taxiservicespring.util.TestDataUtil.ID;
import static com.example.taxiservicespring.util.TestDataUtil.PHONE;
import static com.example.taxiservicespring.util.TestDataUtil.createPerson;
import static com.example.taxiservicespring.util.TestDataUtil.createPersonDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.taxiservicespring.controller.dto.PersonDto;
import com.example.taxiservicespring.service.exception.EntityNotFoundException;
import com.example.taxiservicespring.service.mapper.PersonMapper;
import com.example.taxiservicespring.service.model.Person;
import com.example.taxiservicespring.service.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void findByIdTest() {
        Person person = createPerson();
        when(personRepository.findById(ID)).thenReturn(Optional.of(person));

        PersonDto personDto = personService.getById(ID);

        assertThat(personDto, allOf(
                hasProperty("id", equalTo(person.getId())),
                hasProperty("phone", equalTo(person.getPhone())),
                hasProperty("password", is(nullValue())),
                hasProperty("name", equalTo(person.getName())),
                hasProperty("surname", equalTo(person.getSurname())),
                hasProperty("role", equalTo(person.getRole()))
                ));
    }

    @Test
    void findByIdNotFoundTest() {
        when(personRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> personService.getById(ID));
    }

    @Test
    void findByPhoneTest() {
        Person person = createPerson();
        when(personRepository.findByPhone(PHONE)).thenReturn(Optional.of(person));

        PersonDto personDto = personService.getByPhone(PHONE);

        assertThat(personDto, allOf(
                hasProperty("id", equalTo(person.getId())),
                hasProperty("phone", equalTo(person.getPhone())),
                hasProperty("password", is(nullValue())),
                hasProperty("name", equalTo(person.getName())),
                hasProperty("surname", equalTo(person.getSurname())),
                hasProperty("role", equalTo(person.getRole()))
                ));
    }

    @Test
    void findByPhoneNotFoundTest() {
        when(personRepository.findByPhone(PHONE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> personService.getByPhone(PHONE));
    }

    @Test
    void getAllTest() {
        Person person = createPerson();
        when(personRepository.findAll()).thenReturn(List.of(person));

        List<PersonDto> personDtoList = personService.getAll();

        assertThat(personDtoList, hasSize(1));
        assertThat(personDtoList.get(0), allOf(
                hasProperty("id", equalTo(person.getId())),
                hasProperty("phone", equalTo(person.getPhone())),
                hasProperty("password", is(nullValue())),
                hasProperty("name", equalTo(person.getName())),
                hasProperty("surname", equalTo(person.getSurname())),
                hasProperty("role", equalTo(person.getRole()))
                ));
    }

    @Test
    void createTest() {
        Person person = createPerson();
        PersonDto testPersonDto = createPersonDto();
        when(personRepository.save(person)).thenReturn(person);

        PersonDto personDto = personService.create(testPersonDto);

        assertThat(personDto, allOf(
                hasProperty("id", equalTo(person.getId())),
                hasProperty("phone", equalTo(person.getPhone())),
                hasProperty("password", is(nullValue())),
                hasProperty("name", equalTo(person.getName())),
                hasProperty("surname", equalTo(person.getSurname())),
                hasProperty("role", equalTo(person.getRole()))
                ));
    }

    @Test
    void updateTest() {
        Person person = createPerson();
        Person updatedPerson = createPerson();
        updatedPerson.setName("Jack");
        updatedPerson.setSurname("Smith");
        PersonDto testPersonDto = PersonMapper.INSTANCE.mapPersonDto(updatedPerson);
        when(personRepository.findByPhone(PHONE)).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(updatedPerson);

        PersonDto personDto = personService.update(PHONE, testPersonDto);

        assertThat(personDto, allOf(
                hasProperty("id", equalTo(person.getId())),
                hasProperty("phone", equalTo(person.getPhone())),
                hasProperty("password", is(nullValue())),
                hasProperty("name", equalTo(person.getName())),
                hasProperty("surname", equalTo(person.getSurname())),
                hasProperty("role", equalTo(person.getRole()))
                ));
    }

    @Test
    void updateNotFoundTest() {
        PersonDto personDto = createPersonDto();
        assertThrows(EntityNotFoundException.class, () -> personService.update(PHONE, personDto));
    }

    @Test
    void deleteTest() {
        Person person = createPerson();
        when(personRepository.findByPhone(PHONE)).thenReturn(Optional.of(person));
        personService.delete(PHONE);
        verify(personRepository, times(1)).delete(person);
    }

    @Test
    void deleteNotFoundTest() {
        when(personRepository.findByPhone(PHONE)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> personService.delete(PHONE));
    }
}
