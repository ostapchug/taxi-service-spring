package com.example.taxiservicespring.service.exception;

import com.example.taxiservicespring.service.model.ErrorType;

public class EntityNotFoundException extends ServiceException {
    private static final String DEFAULT_MESSAGE = "Entity is not found!";

    public EntityNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR;
    }
}
