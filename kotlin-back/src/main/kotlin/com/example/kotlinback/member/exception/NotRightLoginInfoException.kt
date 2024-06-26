package com.example.kotlinback.member.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "not right login info")
class NotRightLoginInfoException(message: String) : RuntimeException(message)
