package com.example.kotlinback.member.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern


data class PasswordChangeDto(
    @NotBlank(message = "비밀번호는 필수항목입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{5,16}$", message = "비밀번호는 영문, 숫자, 특수문자를 적어도 하나 이상 포함하는 5~16자의 문자열입니다.")
    val password1: String,
    @NotBlank(message = "비밀번호 확인은 필수항목입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{5,16}$", message = "비밀번호는 영문, 숫자, 특수문자를 적어도 하나 이상 포함하는 5~16자의 문자열입니다.")
    val password2: String
)
