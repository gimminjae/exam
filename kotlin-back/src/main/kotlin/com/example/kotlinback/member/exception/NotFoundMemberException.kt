package com.example.kotlinback.member.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "not found member")
class NotFoundMemberException(message: String?) : RuntimeException(message)
