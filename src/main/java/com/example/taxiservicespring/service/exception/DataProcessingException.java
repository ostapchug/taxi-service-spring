package com.example.taxiservicespring.service.exception;

import com.example.taxiservicespring.service.model.ErrorType;

public class DataProcessingException extends ServiceException {
    private static final String DEFAULT_MESSAGE = "Can't create entity";

    public DataProcessingException() {
        super(DEFAULT_MESSAGE);
    }

    public DataProcessingException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.PROCESSING_ERROR;
    }
}
