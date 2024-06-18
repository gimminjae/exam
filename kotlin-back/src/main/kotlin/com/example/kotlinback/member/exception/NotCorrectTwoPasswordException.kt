package com.example.kotlinback.member.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "two password2 not correct")
class NotCorrectTwoPasswordException(message: String) : RuntimeException(message)
