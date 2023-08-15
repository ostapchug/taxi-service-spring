package com.example.taxiservicespring.service;

import java.util.List;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.CarModelDto;
import com.example.taxiservicespring.controller.dto.CategoryDto;

public interface CarService {

    CarDto getById(long id);

    CategoryDto getCategoryById(int id);

    List<CategoryDto> getAllCategories();

    CarModelDto getCarModelById(long id);
}
