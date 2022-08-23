package com.example.taxiservicespring.controller;

import static com.example.taxiservicespring.util.TestDataUtil.DATE_RANGE;
import static com.example.taxiservicespring.util.TestDataUtil.createTripConfirmDto;
import static com.example.taxiservicespring.util.TestDataUtil.createTripCreateDto;
import static com.example.taxiservicespring.util.TestDataUtil.createTripDto;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.example.taxiservicespring.config.TestConfig;
import com.example.taxiservicespring.controller.dto.TripConfirmDto;
import com.example.taxiservicespring.controller.dto.TripCreateDto;
import com.example.taxiservicespring.controller.dto.TripDto;
import com.example.taxiservicespring.service.TripService;
import com.example.taxiservicespring.service.model.TripStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TripController.class)
@Import(TestConfig.class)
class TripControllerTest {

    @MockBean
    private TripService tripService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getByIdTest() throws Exception {
        TripDto tripDto = createTripDto();
        when(tripService.find(anyLong())).thenReturn(tripDto);

        mockMvc.perform(get("/api/v1/trip/id/" + anyLong()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tripDto.getId()))
            .andExpect(jsonPath("$.personId").value(tripDto.getPersonId()))
            .andExpect(jsonPath("$.originId").value(tripDto.getOriginId()))
            .andExpect(jsonPath("$.destinationId").value(tripDto.getDestinationId()))
            .andExpect(jsonPath("$.distance").value(tripDto.getDistance().doubleValue()))
            .andExpect(jsonPath("$.date").value(tripDto.getDate().format(DateTimeFormatter.ISO_DATE_TIME)))
            .andExpect(jsonPath("$.bill").value(tripDto.getBill().doubleValue()))
            .andExpect(jsonPath("$.status").value(tripDto.getStatus().name()))
            .andExpect(jsonPath("$.cars.length()").value(tripDto.getCars().size()));
    }

    @Test
    void getAllTest() throws Exception {
        TripDto tripDto = createTripDto();
        when(tripService.getAll(any())).thenReturn(new PageImpl<>(List.of(tripDto)));

        mockMvc.perform(get("/api/v1/trip?page=0&size=2&sort=date,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.totalElements").value(1))
            .andExpect(jsonPath("$.content[0].id").value(tripDto.getId()))
            .andExpect(jsonPath("$.content[0].personId").value(tripDto.getPersonId()))
            .andExpect(jsonPath("$.content[0].originId").value(tripDto.getOriginId()))
            .andExpect(jsonPath("$.content[0].destinationId").value(tripDto.getDestinationId()))
            .andExpect(jsonPath("$.content[0].distance").value(tripDto.getDistance().doubleValue()))
            .andExpect(jsonPath("$.content[0].date").value(tripDto.getDate().format(DateTimeFormatter.ISO_DATE_TIME)))
            .andExpect(jsonPath("$.content[0].bill").value(tripDto.getBill().doubleValue()))
            .andExpect(jsonPath("$.content[0].status").value(tripDto.getStatus().name()))
            .andExpect(jsonPath("$.content[0].cars.length()").value(tripDto.getCars().size()));
    }

    @Test
    void getAllByPersonIdTest() throws Exception {
        TripDto tripDto = createTripDto();
        when(tripService.getAllByPersonId(anyLong(), any())).thenReturn(new PageImpl<>(List.of(tripDto)));

        mockMvc.perform(get("/api/v1/trip/person-id/" + tripDto.getPersonId() + "?page=0&size=2&sort=date,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.totalElements").value(1))
            .andExpect(jsonPath("$.content[0].id").value(tripDto.getId()))
            .andExpect(jsonPath("$.content[0].personId").value(tripDto.getPersonId()))
            .andExpect(jsonPath("$.content[0].originId").value(tripDto.getOriginId()))
            .andExpect(jsonPath("$.content[0].destinationId").value(tripDto.getDestinationId()))
            .andExpect(jsonPath("$.content[0].distance").value(tripDto.getDistance().doubleValue()))
            .andExpect(jsonPath("$.content[0].date").value(tripDto.getDate().format(DateTimeFormatter.ISO_DATE_TIME)))
            .andExpect(jsonPath("$.content[0].bill").value(tripDto.getBill().doubleValue()))
            .andExpect(jsonPath("$.content[0].status").value(tripDto.getStatus().name()))
            .andExpect(jsonPath("$.content[0].cars.length()").value(tripDto.getCars().size()));
    }

    @Test
    void getAllByDateTest() throws Exception {
        TripDto tripDto = createTripDto();
        when(tripService.getAllByDate(anyString(), any())).thenReturn(new PageImpl<>(List.of(tripDto)));

        mockMvc.perform(get("/api/v1/trip/date/" + DATE_RANGE + "?page=0&size=2&sort=date,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.totalElements").value(1))
            .andExpect(jsonPath("$.content[0].id").value(tripDto.getId()))
            .andExpect(jsonPath("$.content[0].personId").value(tripDto.getPersonId()))
            .andExpect(jsonPath("$.content[0].originId").value(tripDto.getOriginId()))
            .andExpect(jsonPath("$.content[0].destinationId").value(tripDto.getDestinationId()))
            .andExpect(jsonPath("$.content[0].distance").value(tripDto.getDistance().doubleValue()))
            .andExpect(jsonPath("$.content[0].date").value(tripDto.getDate().format(DateTimeFormatter.ISO_DATE_TIME)))
            .andExpect(jsonPath("$.content[0].bill").value(tripDto.getBill().doubleValue()))
            .andExpect(jsonPath("$.content[0].status").value(tripDto.getStatus().name()))
            .andExpect(jsonPath("$.content[0].cars.length()").value(tripDto.getCars().size()));
    }

    @Test
    void getAllByPersonIdAndDateTest() throws Exception {
        TripDto tripDto = createTripDto();
        when(tripService.getAllByPersonIdAndDate(anyLong(), anyString(), any())).thenReturn(new PageImpl<>(List.of(tripDto)));

        mockMvc.perform(get("/api/v1/trip/" + tripDto.getPersonId() + "/" + DATE_RANGE + "?page=0&size=2&sort=date,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.totalElements").value(1))
            .andExpect(jsonPath("$.content[0].id").value(tripDto.getId()))
            .andExpect(jsonPath("$.content[0].personId").value(tripDto.getPersonId()))
            .andExpect(jsonPath("$.content[0].originId").value(tripDto.getOriginId()))
            .andExpect(jsonPath("$.content[0].destinationId").value(tripDto.getDestinationId()))
            .andExpect(jsonPath("$.content[0].distance").value(tripDto.getDistance().doubleValue()))
            .andExpect(jsonPath("$.content[0].date").value(tripDto.getDate().format(DateTimeFormatter.ISO_DATE_TIME)))
            .andExpect(jsonPath("$.content[0].bill").value(tripDto.getBill().doubleValue()))
            .andExpect(jsonPath("$.content[0].status").value(tripDto.getStatus().name()))
            .andExpect(jsonPath("$.content[0].cars.length()").value(tripDto.getCars().size()));
    }

    @Test
    void createTest() throws Exception {
        TripCreateDto tripCreateDto = createTripCreateDto();
        TripConfirmDto tripConfirmDto = createTripConfirmDto();
        ObjectMapper objectMapper = new ObjectMapper();
        when(tripService.create(tripCreateDto)).thenReturn(tripConfirmDto);

        mockMvc.perform(post("/api/v1/trip", tripCreateDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tripCreateDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.personId").value(tripConfirmDto.getPersonId()))
            .andExpect(jsonPath("$.originId").value(tripConfirmDto.getOriginId()))
            .andExpect(jsonPath("$.destinationId").value(tripConfirmDto.getDestinationId()))
            .andExpect(jsonPath("$.categoryId").value(tripConfirmDto.getCategoryId()))
            .andExpect(jsonPath("$.distance").value(tripConfirmDto.getDistance().doubleValue()))
            .andExpect(jsonPath("$.price").value(tripConfirmDto.getPrice().doubleValue()))
            .andExpect(jsonPath("$.discount").value(tripConfirmDto.getDiscount().doubleValue()))
            .andExpect(jsonPath("$.total").value(tripConfirmDto.getTotal().doubleValue()))
            .andExpect(jsonPath("$.waitTime").value(tripConfirmDto.getWaitTime().format(DateTimeFormatter.ISO_TIME)))
            .andExpect(jsonPath("$.cars.length()").value(tripConfirmDto.getCars().size()));
    }

    @Test
    void createPersonIdNotValidTest() throws Exception {
        TripCreateDto tripCreateDto = createTripCreateDto();
        tripCreateDto.setPersonId(0);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/trip", tripCreateDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tripCreateDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void createOriginIdNotValidTest() throws Exception {
        TripCreateDto tripCreateDto = createTripCreateDto();
        tripCreateDto.setOriginId(0);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/trip", tripCreateDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tripCreateDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void createDestinationIdNotValidTest() throws Exception {
        TripCreateDto tripCreateDto = createTripCreateDto();
        tripCreateDto.setDestinationId(0);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/trip", tripCreateDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tripCreateDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void createSameLocationsTest() throws Exception {
        TripCreateDto tripCreateDto = createTripCreateDto();
        ObjectMapper objectMapper = new ObjectMapper();
        tripCreateDto.setDestinationId(tripCreateDto.getOriginId());

        mockMvc.perform(post("/api/v1/trip", tripCreateDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tripCreateDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void createCapacityNotEnoghTest() throws  Exception {
        TripCreateDto tripCreateDto = createTripCreateDto();
        ObjectMapper objectMapper = new ObjectMapper();
        tripCreateDto.setCapacity(0);

        mockMvc.perform(post("/api/v1/trip", tripCreateDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tripCreateDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void confirmTest() throws Exception {
        TripDto tripDto = createTripDto();
        TripConfirmDto tripConfirmDto = createTripConfirmDto();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        when(tripService.confirm(tripConfirmDto)).thenReturn(tripDto);

        mockMvc.perform(post("/api/v1/trip/confirm", tripConfirmDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tripConfirmDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tripDto.getId()))
            .andExpect(jsonPath("$.personId").value(tripDto.getPersonId()))
            .andExpect(jsonPath("$.originId").value(tripDto.getOriginId()))
            .andExpect(jsonPath("$.destinationId").value(tripDto.getDestinationId()))
            .andExpect(jsonPath("$.distance").value(tripDto.getDistance().doubleValue()))
            .andExpect(jsonPath("$.date").value(tripDto.getDate().format(DateTimeFormatter.ISO_DATE_TIME)))
            .andExpect(jsonPath("$.bill").value(tripDto.getBill().doubleValue()))
            .andExpect(jsonPath("$.status").value(tripDto.getStatus().name()))
            .andExpect(jsonPath("$.cars.length()").value(tripDto.getCars().size()));
    }

    @Test
    void confirmSameLocationsTest() throws  Exception {
        TripConfirmDto tripConfirmDto = createTripConfirmDto();
        tripConfirmDto.setOriginId(tripConfirmDto.getDestinationId());
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mockMvc.perform(post("/api/v1/trip", tripConfirmDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tripConfirmDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void confirmDistanceNotEnoughTest() throws  Exception {
        TripConfirmDto tripConfirmDto = createTripConfirmDto();
        tripConfirmDto.setDistance(BigDecimal.ZERO);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mockMvc.perform(post("/api/v1/trip/confirm", tripConfirmDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tripConfirmDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void confirmCarsEmptyTest() throws  Exception {
        TripConfirmDto tripConfirmDto = createTripConfirmDto();
        tripConfirmDto.setCars(new ArrayList<>());
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mockMvc.perform(post("/api/v1/trip/confirm", tripConfirmDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tripConfirmDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void confirmCarsNullTest() throws  Exception {
        TripConfirmDto tripConfirmDto = createTripConfirmDto();
        tripConfirmDto.setCars(null);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        mockMvc.perform(post("/api/v1/trip/confirm", tripConfirmDto)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tripConfirmDto))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void updateStatusTest() throws Exception {
        TripDto tripDto = createTripDto();
        tripDto.setStatus(TripStatus.ACCEPTED);
        when(tripService.updateStatus(anyLong(), anyString())).thenReturn(tripDto);

        mockMvc.perform(put("/api/v1/trip/status/1/accepted"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tripDto.getId()))
            .andExpect(jsonPath("$.personId").value(tripDto.getPersonId()))
            .andExpect(jsonPath("$.originId").value(tripDto.getOriginId()))
            .andExpect(jsonPath("$.destinationId").value(tripDto.getDestinationId()))
            .andExpect(jsonPath("$.distance").value(tripDto.getDistance().doubleValue()))
            .andExpect(jsonPath("$.date").value(tripDto.getDate().format(DateTimeFormatter.ISO_DATE_TIME)))
            .andExpect(jsonPath("$.bill").value(tripDto.getBill().doubleValue()))
            .andExpect(jsonPath("$.status").value(tripDto.getStatus().name()))
            .andExpect(jsonPath("$.cars.length()").value(tripDto.getCars().size()));
    }
}
