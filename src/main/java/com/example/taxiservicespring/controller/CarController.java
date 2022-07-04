package com.example.taxiservicespring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.taxiservicespring.controller.dto.CarDto;
import com.example.taxiservicespring.controller.dto.CarModelDto;
import com.example.taxiservicespring.controller.dto.CategoryDto;
import com.example.taxiservicespring.service.CarService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CarController {
	
	private final CarService carService;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/car/id/{id}")
	public CarDto getById(@PathVariable long id){
		
		log.info("request car with id {}", id);
		
		return carService.find(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/car/category/id/{id}")
	public CategoryDto getCategoryById(@PathVariable int id){
		
		log.info("request car category with id {}", id);
		
		return carService.findCategory(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/car/category")
	public List<CategoryDto> getAllCategories(){
		
		log.info("request list of all car categories");
		
		return carService.findAllCategories();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/car/car-model/id/{id}")
	public CarModelDto getCarModelById(@PathVariable long id){
		
		log.info("request car model with id {}", id);
		
		return carService.findCarModel(id);
	}

}
