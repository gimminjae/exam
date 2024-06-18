package com.example.kotlinback.global.mail.dto

class MailTo(
    val address: String? = "",
    val title: String? = "",
    val message: String? = ""
) {
    companion object {
        fun sendEmailAuthCode(authCode: String = "", email: String = ""): MailTo {
            return MailTo(
                title = "인증코드입니다.",
                message = "인증코드는 %s 입니다.".format(authCode),
                address = email
            )
        }

        fun sendTemporaryPw(email: String = "", password: String = ""): MailTo {
            return MailTo(
                title = "Forgot Password: 비밀번호 변경",
                message = "변경된 비밀번호는 %s 입니다.".format(password),
                address = email
            )
        }
    }
}
