package com.example.taxiservicespring;

import static com.example.taxiservicespring.util.TestDataUtil.ID;
import static com.example.taxiservicespring.util.TestDataUtil.PHONE;
import static com.example.taxiservicespring.util.TestDataUtil.createPersonDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import com.example.taxiservicespring.controller.dto.PersonDto;

@Profile("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class TaxiServiceSpringApplicationTests {

    @Value("http://localhost:${local.server.port}/api/v1/person")
    private String baseUrl;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Order(1)
    void createPersonTest() {
        PersonDto testPersonDto = createPersonDto();
        PersonDto personDto = testRestTemplate.postForObject(baseUrl, testPersonDto, PersonDto.class);

        assertThat(personDto, allOf(
                hasProperty("phone", equalTo(testPersonDto.getPhone())),
                hasProperty("password", is(nullValue())),
                hasProperty("name", equalTo(testPersonDto.getName())),
                hasProperty("surname", equalTo(testPersonDto.getSurname())),
                hasProperty("role", equalTo(testPersonDto.getRole()))));
    }

    @Test
    @Order(2)
    void getPersonByIdTest() {
        PersonDto testPersonDto = createPersonDto();
        PersonDto personDto = testRestTemplate.getForObject(baseUrl + "/id/" + ID, PersonDto.class);

        assertThat(personDto, allOf(
                hasProperty("id", equalTo(testPersonDto.getId())),
                hasProperty("phone", equalTo(testPersonDto.getPhone())),
                hasProperty("password", is(nullValue())),
                hasProperty("name", equalTo(testPersonDto.getName())),
                hasProperty("surname", equalTo(testPersonDto.getSurname())),
                hasProperty("role", equalTo(testPersonDto.getRole()))));
    }

    @Test
    @Order(3)
    void getPersonByPhoneTest() {
        PersonDto testPersonDto = createPersonDto();
        PersonDto personDto = testRestTemplate.getForObject(baseUrl + "/phone/" + PHONE, PersonDto.class);

        assertThat(personDto, allOf(
                hasProperty("id", equalTo(testPersonDto.getId())),
                hasProperty("phone", equalTo(testPersonDto.getPhone())),
                hasProperty("password", is(nullValue())),
                hasProperty("name", equalTo(testPersonDto.getName())),
                hasProperty("surname", equalTo(testPersonDto.getSurname())),
                hasProperty("role", equalTo(testPersonDto.getRole()))));
    }

    @Test
    @Order(4)
    void getAllTest() {
        PersonDto testPersonDto = createPersonDto();
        List<PersonDto> personDtoList = testRestTemplate
                .exchange(baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<PersonDto>>() {
                }).getBody();

        assertThat(personDtoList, hasSize(1));
        assertThat(personDtoList.get(0), allOf(
                hasProperty("id", equalTo(testPersonDto.getId())),
                hasProperty("phone", equalTo(testPersonDto.getPhone())),
                hasProperty("password", is(nullValue())),
                hasProperty("name", equalTo(testPersonDto.getName())),
                hasProperty("surname", equalTo(testPersonDto.getSurname())),
                hasProperty("role", equalTo(testPersonDto.getRole()))));
    }

    @Test
    @Order(5)
    void updatePersonTest() {
        PersonDto testPersonDto = createPersonDto();
        testPersonDto.setSurname("Smith");
        HttpEntity<PersonDto> entity = new HttpEntity<>(testPersonDto);
        PersonDto personDto = testRestTemplate.exchange(baseUrl + "/" + PHONE, HttpMethod.PUT, entity, PersonDto.class)
                .getBody();

        assertThat(personDto, allOf(
                hasProperty("id", equalTo(testPersonDto.getId())),
                hasProperty("phone", equalTo(testPersonDto.getPhone())),
                hasProperty("password", is(nullValue())),
                hasProperty("name", equalTo(testPersonDto.getName())),
                hasProperty("surname", equalTo(testPersonDto.getSurname())),
                hasProperty("role", equalTo(testPersonDto.getRole()))
                ));
    }
}
