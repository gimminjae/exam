package com.example.kotlinback.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    @NotBlank(message = "아이디는 필수항목입니다.")
    @Size(min = 5, max = 16, message = "아이디는 5 ~16자 이어야 합니다.")
    private String username;
    @NotBlank(message = "닉네임은 필수항목입니다.")
    @Pattern(regexp = "^[A-Za-z0-9가-힣]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String nickname;
    @NotBlank(message = "이메일은 필수항목입니다.")
    @Email
    private String email;
    @NotBlank(message = "비밀번호는 필수항목입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{5,16}$", message = "비밀번호는 영문, 숫자, 특수문자를 적어도 하나 이상 포함하는 5~16자의 문자열입니다.")
    private String password1;
    @NotBlank(message = "비밀번호 확인은 필수항목입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{5,16}$", message = "비밀번호는 영문, 숫자, 특수문자를 적어도 하나 이상 포함하는 5~16자의 문자열입니다.")
    private String password2;
    private String memType;
}
