package com.example.taxiservicespring.service.model;

import lombok.Data;

@Data
public class Person {
    private long id;
    private String phone;
    private String password;
    private String name;
    private String surname;
    private int roleId;
}
