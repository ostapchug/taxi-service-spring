package com.example.taxiservicespring.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {
    private long id;
    private String phone;
    private String password;
    private String name;
    private String surname;
    @Builder.Default
    private Role role = Role.CLIENT;
}
