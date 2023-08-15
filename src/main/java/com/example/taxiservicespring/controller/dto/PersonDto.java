package com.example.taxiservicespring.controller.dto;

import com.example.taxiservicespring.service.model.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PersonDto {
    private long id;
    private String phone;
    private String password;
    private String name;
    private String surname;
    private Role role;
}
