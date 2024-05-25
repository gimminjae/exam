package com.boblogservice.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "아이디는 필수항목입니다.")
    private String username;
    @NotBlank(message = "비밀번호는 필수항목입니다.")
    private String password;
}
