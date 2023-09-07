package com.example.taxiservicespring.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.example.taxiservicespring.service.model.Role;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PersonDto {
    private static final String PHONE_PATTERN = "[0-9]{10}";
    private static final String TEXT_PATTERN = "[A-Z][a-z]+|[\\p{IsCyrillic}&&\\p{Lu}][\\p{IsCyrillic}&&\\p{Ll}]+";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private long id;

    @NotEmpty(message = "{phone.notempty}")
    @Pattern(message = "{phone.format}", regexp = PHONE_PATTERN)
    private String phone;

    @NotEmpty(message = "{password.notempty}")
    @Pattern(message = "{password.format}", regexp = PASSWORD_PATTERN)
    private String password;

    @Pattern(message = "{name.format}", regexp = TEXT_PATTERN)
    private String name;

    @Pattern(message = "{name.format}", regexp = TEXT_PATTERN)
    private String surname;

    private Role role;
}
