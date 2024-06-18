package com.example.kotlinback.member.authcode

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.time.LocalDateTime

@RedisHash(value = "authCode", timeToLive = 60 * 5) //5분

class AuthCode(
    @Id
    var email: String = "",
    var code: String = "",
    var createdDateTime: LocalDateTime?,
    var expiredDateTime: LocalDateTime?,
    var certifiedYn: Boolean?
) {

    @Indexed
    private var refreshToken: String = ""

    fun update(expiredDateTime: LocalDateTime, sb: String) {
        this.refreshToken = sb
        this.expiredDateTime = expiredDateTime
    }

    companion object {
        fun from(code: String, email: String): AuthCode {
            return AuthCode(
                email = email,
                createdDateTime = LocalDateTime.now(),
                expiredDateTime = LocalDateTime.now().plusMinutes(5),
                certifiedYn = false
            )
        }
    }
}
