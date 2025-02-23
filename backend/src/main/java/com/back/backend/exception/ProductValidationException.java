package com.back.backend.exception;

import org.springframework.http.HttpStatus;

public class ProductValidationException extends ProductException {
    public ProductValidationException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
