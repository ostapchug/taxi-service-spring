package com.example.taxiservicespring.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class CategoryDto {
	
	@JsonProperty(access = Access.READ_ONLY)
	private int id;
	private String name;

}
