package com.example.kotlinback.global.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "validation failed")
class FormValidationException(message: String = "") : RuntimeException(message)
