package com.example.kotlinback.member.dto

import jakarta.validation.constraints.NotBlank
import lombok.*

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class LoginDto {
    private val username: @NotBlank(message = "아이디는 필수항목입니다.") String? = null
    private val password: @NotBlank(message = "비밀번호는 필수항목입니다.") String? = null
}
