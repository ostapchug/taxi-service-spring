package com.example.taxiservicespring.service.exception;

import com.example.taxiservicespring.service.model.ErrorType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class ServiceException extends RuntimeException {
    private ErrorType errorType;

    ServiceException(String message) {
        super(message);
    }

    public ErrorType getErrorType() {
        return ErrorType.FATAL_ERROR;
    }
}
