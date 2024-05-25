package com.boblogservice.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "two password2 not correct")
public class NotCorrectTwoPasswordException extends RuntimeException {
    public NotCorrectTwoPasswordException(String message) {
        super(message);
    }
}