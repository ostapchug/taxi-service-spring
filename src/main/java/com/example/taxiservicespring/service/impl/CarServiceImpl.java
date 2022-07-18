package com.example.taxiservicespring.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.CarModelDto;
import com.example.taxiservicespring.controller.dto.CategoryDto;
import com.example.taxiservicespring.service.CarService;
import com.example.taxiservicespring.service.exception.EntityNotFoundException;
import com.example.taxiservicespring.service.mapper.CarMapper;
import com.example.taxiservicespring.service.mapper.CarModelMapper;
import com.example.taxiservicespring.service.mapper.CategoryMapper;
import com.example.taxiservicespring.service.model.Car;
import com.example.taxiservicespring.service.model.CarModel;
import com.example.taxiservicespring.service.model.Category;
import com.example.taxiservicespring.service.repository.CarModelRepository;
import com.example.taxiservicespring.service.repository.CarRepository;
import com.example.taxiservicespring.service.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CategoryRepository categoryRepository;
    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;

    @Override
    public CarDto find(long id) {
        log.info("get car by id {}", id);
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car is not found!"));
        return CarMapper.INSTANCE.mapCarDto(car);
    }

    @Override
    public CategoryDto findCategory(int id) {
        log.info("get category by id {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category is not found!"));
        return CategoryMapper.INSTANCE.mapCategoryDto(category);
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        log.info("get all categories");
        return categoryRepository.findAllCategories()
                .stream()
                .map(category -> CategoryMapper.INSTANCE.mapCategoryDto(category))
                .collect(Collectors.toList());
    }

    @Override
    public CarModelDto findCarModel(long id) {
        log.info("get car model by id {}", id);        
        CarModel carModel = carModelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car model is not found!"));
        return CarModelMapper.INSTANCE.mapCarModelDto(carModel);
    }
}
