package com.example.taxiservicespring.service.exception;

import com.example.taxiservicespring.service.model.ErrorType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ErrorType errorType = ErrorType.FATAL_ERROR;

    ServiceException(String message) {
        super(message);
    }
}
