package com.example.kotlinback.member.authcode

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.time.LocalDateTime

@RedisHash(value = "authCode", timeToLive = 60 * 5) //5분

class AuthCode(
    @Id
    var email: String? = null,
    var code: String? = null,
    var createdDateTime: LocalDateTime? = null,
    var expiredDateTime: LocalDateTime? = null,
    var certifiedYn: Boolean? = null
) {

    @Indexed
    private var refreshToken: String? = null
    fun setExpiredDateTime(expiredDateTime: LocalDateTime?) {
        this.expiredDateTime = expiredDateTime
    }

    fun setRefreshToken(refreshToken: String?) {
        this.refreshToken = refreshToken
    }

    fun setCertifiedYn(certifiedYn: Boolean?) {
        this.certifiedYn = certifiedYn
    }

    fun update(expiredDateTime: LocalDateTime?, sb: String?) {
        setRefreshToken(sb)
        setExpiredDateTime(expiredDateTime)
    }

    companion object {
        fun from(code: String?, email: String?): AuthCode {
            return AuthCode(
                email = email,
                createdDateTime = LocalDateTime.now(),
                expiredDateTime = LocalDateTime.now().plusMinutes(5),
                certifiedYn = false
            )
        }
    }
}
