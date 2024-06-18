package com.example.kotlinback.member.entity

import com.example.kotlinback.member.dto.MemberDto
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIncludeProperties
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import java.time.LocalDateTime

@JsonIncludeProperties("nickname", "role", "createDateTime", "memId", "username")
class AuthUser(memberDto: MemberDto, authorities: List<GrantedAuthority>) :
    User(memberDto.memId, memberDto.password, authorities) {
    var memId: String = ""
    var nickname: String = ""

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    var createDateTime: LocalDateTime?
    var role: String = ""
    var usersname: String = ""

    init {
        memId = memberDto.memId
        nickname = memberDto.nickname
        role = authorities[0].toString()
        createDateTime = memberDto.createDateTime
        usersname = memberDto.username
    }

//    override fun getAuthorities(): Set<GrantedAuthority> {
//        return HashSet(super.getAuthorities())
//    }
}
