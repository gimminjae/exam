package com.boblogservice.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "nickname exist already")
public class AlreadyExistMemberException extends RuntimeException {
    public AlreadyExistMemberException(String message) {
        super(message);
    }
}