package com.example.taxiservicespring.controller;

import static com.example.taxiservicespring.util.TestDataUtil.ID;
import static com.example.taxiservicespring.util.TestDataUtil.PHONE;
import static com.example.taxiservicespring.util.TestDataUtil.createPersonDto;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.example.taxiservicespring.config.TestConfig;
import com.example.taxiservicespring.controller.dto.PersonDto;
import com.example.taxiservicespring.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
@Import(TestConfig.class)
class PersonControllerTest {

    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getByIdTest() throws Exception {
        PersonDto personDto = createPersonDto();
        when(personService.find(ID)).thenReturn(personDto);

        mockMvc.perform(get("/api/v1/person/id/" + ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    void getByPhoneTest() throws Exception {
        PersonDto personDto = createPersonDto();
        when(personService.find(PHONE)).thenReturn(personDto);

        mockMvc.perform(get("/api/v1/person/phone/" + PHONE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.phone").value(PHONE));
    }

    @Test
    void getAllTest() throws Exception {
        PersonDto personDto = createPersonDto();
        when(personService.getAll()).thenReturn(List.of(personDto));

        mockMvc.perform(get("/api/v1/person"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void createTest() throws Exception {
        PersonDto personDto = createPersonDto();
        ObjectMapper objectMapper = new ObjectMapper();
        when(personService.create(personDto)).thenReturn((personDto));

        mockMvc.perform(post("/api/v1/person", personDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(personDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ID))
            .andExpect(jsonPath("$.phone").value(PHONE));
    }

    @Test
    void createWithEmptyPhoneTest() throws Exception {
        PersonDto personDto = createPersonDto();
        personDto.setPhone(null);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/person", personDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(personDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void createWithInvalidPhoneTest() throws Exception {
        PersonDto personDto = createPersonDto();
        personDto.setPhone("012345678");
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/person", personDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(personDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void createWithEmptyPasswordTest() throws Exception {
        PersonDto personDto = createPersonDto();
        personDto.setPassword(null);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/person", personDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(personDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void createWithInvalidPasswordTest() throws Exception {
        PersonDto personDto = createPersonDto();
        personDto.setPassword("Client");
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/person", personDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(personDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void createWithInvalidNameTest() throws Exception {
        PersonDto personDto = createPersonDto();
        personDto.setName("j");
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/person", personDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(personDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void createWithInvalidSurnameTest() throws Exception {
        PersonDto personDto = createPersonDto();
        personDto.setSurname("d");
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/person", personDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(personDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void updateTest() throws Exception {
        PersonDto personDto = createPersonDto();
        ObjectMapper objectMapper = new ObjectMapper();
        when(personService.update(PHONE, personDto)).thenReturn((personDto));

        mockMvc.perform(put("/api/v1/person/" + PHONE, personDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(personDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ID))
            .andExpect(jsonPath("$.phone").value(PHONE));
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/api/v1/person/" + PHONE))
            .andExpect(status().isNoContent());

        verify(personService, times(1)).delete(any());
    }
}
