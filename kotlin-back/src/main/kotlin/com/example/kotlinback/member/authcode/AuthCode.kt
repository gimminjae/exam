package com.example.kotlinback.member.authcode

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.time.LocalDateTime

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "authCode", timeToLive = 60 * 5) //5분

class AuthCode {
    @Id
    private val email: String? = null
    private val code: String? = null
    private val createdDateTime: LocalDateTime? = null
    private var expiredDateTime: LocalDateTime? = null
    private var certifiedYn: Boolean? = null

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
            return AuthCode.builder()
                .email(email)
                .createdDateTime(LocalDateTime.now())
                .expiredDateTime(LocalDateTime.now().plusMinutes(5))
                .code(code)
                .certifiedYn(false)
                .build()
        }
    }
}
