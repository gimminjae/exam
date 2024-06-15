package com.example.kotlinback.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "nickname exist already")
public class NotCorrectEmailAuthCodeException extends RuntimeException {
    public NotCorrectEmailAuthCodeException(String message) {
        super(message);
    }
}