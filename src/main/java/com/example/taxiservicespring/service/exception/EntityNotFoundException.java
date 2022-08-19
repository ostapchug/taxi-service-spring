package com.example.taxiservicespring.service.exception;

import com.example.taxiservicespring.service.model.ErrorType;

public class EntityNotFoundException extends ServiceException {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MESSAGE = "Entity is not found!";

    public EntityNotFoundException() {
        this(DEFAULT_MESSAGE);
    }

    public EntityNotFoundException(String message) {
        super(message);
        setErrorType(ErrorType.DATABASE_ERROR);
    }
}
