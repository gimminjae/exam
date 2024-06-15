package com.example.kotlinback.member.refresh.entity

import lombok.Builder
import lombok.Getter
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@Builder
@Getter
@RedisHash(value = "refresh", timeToLive = 60 * 60 * 24 * 14) //refresh token 기간 -> 2주
//@RedisHash(value = "refresh", timeToLive = 60) //refresh token 기간 -> 1분

class RefreshToken {
    @Id
    private val id: String? = null

    @Indexed
    private var refreshToken: String? = null
    fun setRefreshToken(refreshToken: String?) {
        this.refreshToken = refreshToken
    }

    fun update(sb: String?) {
        setRefreshToken(sb)
    }

    companion object {
        fun from(id: String?, refreshToken: String?): RefreshToken {
            return RefreshToken.builder()
                .id(id)
                .refreshToken(refreshToken)
                .build()
        }
    }
}
