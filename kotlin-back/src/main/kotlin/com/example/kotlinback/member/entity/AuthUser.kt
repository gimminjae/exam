package com.example.kotlinback.member.entity

import com.example.kotlinback.k.member.dto.MemberDto
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIncludeProperties
import lombok.Getter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import java.time.LocalDateTime

@Getter
@JsonIncludeProperties("nickname", "role", "createDateTime", "memId", "username")
class AuthUser(memberDto: MemberDto?, authorities: List<GrantedAuthority?>?) :
    User(memberDto.getMemId(), memberDto.getPassword(), authorities) {
    private val memId: String
    private val nickname: String

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private val createDateTime: LocalDateTime
    private val role: String
    private val username: String

    init {
        memId = memberDto.getMemId()
        nickname = memberDto.getNickname()
        role = authorities!![0].toString()
        createDateTime = memberDto.getCreateDateTime()
        this.username = memberDto.getUsername()
    }

    override fun getAuthorities(): Set<GrantedAuthority> {
        return HashSet(super.getAuthorities())
    }
}
