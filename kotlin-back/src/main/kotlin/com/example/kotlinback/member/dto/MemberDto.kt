package com.example.kotlinback.member.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.LocalDateTime

data class MemberDto(
    val memId: String? = null,
    val createDateTime: LocalDateTime? = null,
    val updateDateTime: LocalDateTime? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val nickname: String? = null,
    val memType: String? = null,
    val useYn: Boolean? = null,
    val role: String? = null
) {
    val authorities: List<GrantedAuthority>
        get() {
            val authorities: MutableList<GrantedAuthority> = ArrayList()
            if (this.role == "ADMIN") {
                authorities.add(SimpleGrantedAuthority("ADMIN"))
            } else if (this.role == "SUBADMIN") {
                authorities.add(SimpleGrantedAuthority("SUBADMIN"))
            } else {
                authorities.add(SimpleGrantedAuthority("MEMBER"))
            }
            return authorities
        }

    companion object {
        fun from(signUpDto: SignUpDto?): MemberDto {
            return MemberDto(
                username = signUpDto?.username,
                nickname = signUpDto?.nickname,
                password = signUpDto?.password1,
                memType = signUpDto?.memType,
                email = signUpDto?.email
            )
        }
    }
}
