package com.example.kotlinback.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "nickname exist already")
public class NotCertifiedEmailException extends RuntimeException {
    public NotCertifiedEmailException(String message) {
        super(message);
    }
}