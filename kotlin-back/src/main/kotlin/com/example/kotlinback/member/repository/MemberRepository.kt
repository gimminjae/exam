package com.example.kotlinback.member.repository

import com.example.kotlinback.k.member.entity.Member
import java.util.*

interface MemberRepository {
    fun save(member: Member?): Member?
    fun findByUsername(username: String?): Optional<Member?>?
    fun findByNickname(nickname: String?): Optional<Member?>?
    fun findById(id: String?): Optional<Member?>?
    fun findByEmail(email: String?): Optional<Member?>
}
