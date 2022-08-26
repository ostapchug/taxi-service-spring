package com.example.taxiservicespring.controller;

import static com.example.taxiservicespring.util.TestDataUtil.createLocationDto;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.example.taxiservicespring.config.TestConfig;
import com.example.taxiservicespring.controller.dto.LocationDto;
import com.example.taxiservicespring.service.LocationService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LocationController.class)
@Import(TestConfig.class)
class LocationControllerTest {

    @MockBean
    private LocationService locationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getByIdTest() throws Exception {
        LocationDto locationDto = createLocationDto();
        when(locationService.getById(anyLong())).thenReturn(locationDto);

        mockMvc.perform(get("/api/v1/location/id/" + anyLong()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(locationDto.getId()))
            .andExpect(jsonPath("$.streetName").value(locationDto.getStreetName()))
            .andExpect(jsonPath("$.streetNumber").value(locationDto.getStreetNumber()));
    }

    @Test
    void getAllTest() throws Exception {
        LocationDto locationDto = createLocationDto();
        when(locationService.getAll()).thenReturn(List.of(locationDto));

        mockMvc.perform(get("/api/v1/location"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(1));
    }
}
