package com.example.kotlinback.global.jwt.provider

import com.example.kotlinback.global.config.AppConfig
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import kotlin.collections.HashMap

@Component
class JwtProvider {
    private val secretKey: SecretKey? = null
    fun generateAccessToken(claims: Map<String, Any>, seconds: Int): String {
        val now = Date().time
        val accessTokenExpiresIn = Date(now + 1000L * seconds)
        return Jwts.builder()
            .claim("body", jsonToStr(claims))
            .setExpiration(accessTokenExpiresIn)
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact()
    }

    fun verify(token: String?): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun getClaims(token: String?): Map<String, Any> {
        val body = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .get("body", String::class.java)
        return jsonToMap(body)
    }

    private fun jsonToMap(jsonStr: String): Map<String, Any> {
        return try {
            objectMapper.readValue(jsonStr, LinkedHashMap::class.java) as Map<String, Any>
        } catch (e: JsonProcessingException) {
            throw DataIntegrityViolationException("..")
        }
    }

    companion object {
        private val objectMapper: ObjectMapper
            get() = AppConfig.objectMapper()

        fun jsonToStr(claims: Map<String, Any>): Any {
            val objectMapper = ObjectMapper()
            return try {
                objectMapper.writeValueAsString(claims)
            } catch (e: JsonProcessingException) {
                //임시로 아무 예외 선언
                throw DataIntegrityViolationException("예상치 못한 에러가 발생했습니다.")
            }
        }
    }
}
