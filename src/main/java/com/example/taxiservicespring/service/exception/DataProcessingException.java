package com.example.taxiservicespring.service.exception;

import com.example.taxiservicespring.service.model.ErrorType;

public class DataProcessingException extends ServiceException {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MESSAGE = "Something went wrong!";

    public DataProcessingException() {
        this(DEFAULT_MESSAGE);
    }

    public DataProcessingException(String message) {
        super(message);
        setErrorType(ErrorType.PROCESSING_ERROR);
    }
}
