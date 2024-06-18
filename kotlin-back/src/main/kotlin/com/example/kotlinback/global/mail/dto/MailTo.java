package com.example.kotlinback.global.mail.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MailTo {
    private String address;
    private String title;
    private String message;

    public static MailTo sendEmailAuthCode(String authCode, String email) {
        return MailTo.builder()
                .title("인증코드입니다.")
                .message("인증코드는 %s 입니다.".formatted(authCode))
                .address(email)
                .build();
    }
    public static MailTo sendTemporaryPw(String email, String password) {
        return MailTo.builder()
                .title("Forgot Password: 비밀번호 변경")
                .message("변경된 비밀번호는 %s 입니다.".formatted(password))
                .address(email)
                .build();
    }
}
