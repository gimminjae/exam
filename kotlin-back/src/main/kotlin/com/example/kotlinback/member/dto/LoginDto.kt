package com.example.kotlinback.member.dto

import jakarta.validation.constraints.NotBlank

class LoginDto(
    @NotBlank(message = "아이디는 필수항목입니다.")
    val username: String = "",
    @NotBlank(message = "비밀번호는 필수항목입니다.")
    val password: String = ""
)
