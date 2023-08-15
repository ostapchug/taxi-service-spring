package com.example.taxiservicespring.service.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {
    private String message;
    private ErrorType errorType;
    private LocalDateTime timestamp;
}
