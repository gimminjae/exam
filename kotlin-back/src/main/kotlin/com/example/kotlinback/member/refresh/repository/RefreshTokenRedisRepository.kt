package com.example.kotlinback.member.refresh.repository

import com.example.kotlinback.k.member.refresh.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
interface RefreshTokenRedisRepository : CrudRepository<RefreshToken?, String?> {
    fun findByRefreshToken(refreshToken: String?): Optional<RefreshToken?>?
}
