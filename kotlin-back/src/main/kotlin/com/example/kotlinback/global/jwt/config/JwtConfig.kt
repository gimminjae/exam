package com.example.kotlinback.global.jwt.config

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import javax.crypto.SecretKey

@Configuration
class JwtConfig {
    @Value("\${spring.custom.secretKey}")
    private val secretKeyPlain: String? = null

    @Bean
    fun jwtSecretKey(): SecretKey {
        val keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain!!.toByteArray())
        return Keys.hmacShaKeyFor(keyBase64Encoded.toByteArray())
    }
}
