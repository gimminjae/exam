package com.boblogservice.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "validation failed")
public class FormValidationException extends RuntimeException {
    public FormValidationException(String message) {
        super(message);
    }
}
