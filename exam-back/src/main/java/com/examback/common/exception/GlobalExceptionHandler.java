package com.examback.common.exception;

import com.examback.member.exception.NotCorrectTwoPasswordException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotCorrectTwoPasswordException.class)
    public ResponseEntity<Map<String, List<String>>> handlerNotCorrectTwoPasswordException(NotCorrectTwoPasswordException e) {
        return new ResponseEntity<>(Map.of("errors", List.of(e.getMessage())), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(FormValidationException.class)
    public ResponseEntity<Map<String, List<String>>> handlerFormValidationException(FormValidationException e) {
        String s = e.getMessage();
        List<String> messages = Arrays.stream(s.split("\n")).toList();
        return new ResponseEntity<>(Map.of("errors", messages), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleNullPointerException(IllegalStateException e) {
        return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
