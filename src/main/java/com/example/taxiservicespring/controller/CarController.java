package com.example.taxiservicespring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.example.taxiservicespring.api.CarApi;
import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.CarModelDto;
import com.example.taxiservicespring.controller.dto.CategoryDto;
import com.example.taxiservicespring.service.CarService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CarController implements CarApi {
    private final CarService carService;

    @Override
    public CarDto getById(long id) {
        log.info("request car with id {}", id);
        return carService.find(id);
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        log.info("request car category with id {}", id);
        return carService.findCategory(id);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        log.info("request list of all car categories");
        return carService.findAllCategories();
    }

    @Override
    public CarModelDto getCarModelById(long id) {
        log.info("request car model with id {}", id);
        return carService.findCarModel(id);
    }
}
