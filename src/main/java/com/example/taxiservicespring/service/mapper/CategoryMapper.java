package com.example.taxiservicespring.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.taxiservicespring.controller.dto.CategoryDto;
import com.example.taxiservicespring.service.model.Category;

@Mapper
public interface CategoryMapper {
	
	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
	
	CategoryDto mapCategoryDto(Category category);

}
