package com.example.taxiservicespring.service;

import java.util.List;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.CarModelDto;
import com.example.taxiservicespring.controller.dto.CategoryDto;

public interface CarService {
	
	CarDto find(long id);
	
	CategoryDto findCategory(int id);
	
	List<CategoryDto> findAllCategories();
	
	CarModelDto findCarModel(long id);	

}
