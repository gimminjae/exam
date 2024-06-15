package com.example.kotlinback.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "validation failed")
public class FormValidationException extends RuntimeException {
    public FormValidationException(String message) {
        super(message);
    }
}
