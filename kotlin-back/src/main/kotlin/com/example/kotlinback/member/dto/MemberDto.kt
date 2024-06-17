package com.example.kotlinback.member.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.LocalDate
import java.time.LocalDateTime

data class MemberDto(
    val memId: String = "",
    val createDateTime: LocalDateTime?,
    val updateDateTime: LocalDateTime?,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val nickname: String = "",
    val memType: String = "",
    val useYn: Boolean = true,
    val role: String = ""
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
        fun from(signUpDto: SignUpDto): MemberDto {
            return MemberDto(
                username = signUpDto.username,
                nickname = signUpDto.nickname,
                password = signUpDto.password1,
                memType = signUpDto.memType,
                email = signUpDto.email,
                createDateTime = LocalDateTime.now(),
                updateDateTime = LocalDateTime.now()
            )
        }
    }
}
