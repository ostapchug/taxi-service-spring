package com.example.taxiservicespring.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class PersonDto {
	
	@JsonProperty(access = Access.READ_ONLY)
	private long id;
	private String phone;
	private String password;
	private String name;
	private String surname;
	private String role;

}
