package com.example.kotlinback.global.exception

import com.example.kotlinback.member.exception.NotCorrectTwoPasswordException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.nio.file.AccessDeniedException
import java.util.*

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(NotCorrectTwoPasswordException::class)
    fun handlerNotCorrectTwoPasswordException(e: NotCorrectTwoPasswordException): ResponseEntity<Map<String, List<String>>> {
        return ResponseEntity(mapOf("errors" to listOf(e.message ?: "")), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(FormValidationException::class)
    fun handlerFormValidationException(e: FormValidationException): ResponseEntity<Map<String, List<String>>> {
        val s = e.message
        val messages = Arrays.stream(s!!.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()).toList()
        return ResponseEntity(mapOf("errors" to messages), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleNullPointerException(e: IllegalStateException): ResponseEntity<Map<String, String>> {
        return ResponseEntity(mapOf("message" to (e.message ?: "")), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(e: DataIntegrityViolationException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }
}
