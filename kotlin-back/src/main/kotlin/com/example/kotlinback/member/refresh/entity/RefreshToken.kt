package com.example.kotlinback.member.refresh.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "refresh", timeToLive = 60 * 60 * 24 * 14) //refresh token 기간 -> 2주
//@RedisHash(value = "refresh", timeToLive = 60) //refresh token 기간 -> 1분

class RefreshToken(
    @Id
    val id: String = "",

    @Indexed
    var refreshToken: String = ""
) {
    fun setRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    fun update(sb: String) {
        setRefreshToken(sb)
    }

    companion object {
        fun from(id: String, refreshToken: String): RefreshToken {
            return RefreshToken(
                id = id,
                refreshToken = refreshToken
            )
        }
    }
}
