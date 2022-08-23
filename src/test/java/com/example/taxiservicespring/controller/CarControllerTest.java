package com.example.taxiservicespring.controller;

import static com.example.taxiservicespring.util.TestDataUtil.createCarDto;
import static com.example.taxiservicespring.util.TestDataUtil.createCarModelDto;
import static com.example.taxiservicespring.util.TestDataUtil.createCategoryDto;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.CarModelDto;
import com.example.taxiservicespring.controller.dto.CategoryDto;
import com.example.taxiservicespring.service.CarService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
@Import(TestConfig.class)
class CarControllerTest {

    @MockBean
    private CarService carService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getByIdTest() throws Exception {
        CarDto carDto = createCarDto();
        when(carService.find(anyLong())).thenReturn(carDto);

        mockMvc.perform(get("/api/v1/car/id/" + anyLong()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(carDto.getId()))
            .andExpect(jsonPath("$.regNumber").value(carDto.getRegNumber()))
            .andExpect(jsonPath("$.modelId").value(carDto.getModelId()))
            .andExpect(jsonPath("$.categoryId").value(carDto.getCategoryId()))
            .andExpect(jsonPath("$.locationId").value(carDto.getLocationId()))
            .andExpect(jsonPath("$.status").value(carDto.getStatus().name()));
    }

    @Test
    void getCategoryByIdTest() throws Exception {
        CategoryDto categoryDto = createCategoryDto();
        when(carService.findCategory(anyInt())).thenReturn(categoryDto);

        mockMvc.perform(get("/api/v1/car/category/id/" + anyInt()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(categoryDto.getId()))
            .andExpect(jsonPath("$.name").value(categoryDto.getName()));
    }

    @Test
    void getAllCategoriesTest() throws Exception {
        CategoryDto categoryDto = createCategoryDto();
        when(carService.findAllCategories()).thenReturn(List.of(categoryDto));

        mockMvc.perform(get("/api/v1/car/category"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getCarModelByIdTest() throws Exception {
        CarModelDto carModelDto = createCarModelDto();
        when(carService.findCarModel(anyLong())).thenReturn(carModelDto);

        mockMvc.perform(get("/api/v1/car/car-model/id/" + anyLong()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(carModelDto.getId()))
            .andExpect(jsonPath("$.brand").value(carModelDto.getBrand()))
            .andExpect(jsonPath("$.name").value(carModelDto.getName()))
            .andExpect(jsonPath("$.color").value(carModelDto.getColor()))
            .andExpect(jsonPath("$.year").value(carModelDto.getYear()));
    }
}
