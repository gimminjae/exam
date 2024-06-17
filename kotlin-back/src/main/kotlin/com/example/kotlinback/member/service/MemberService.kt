package com.example.kotlinback.member.service

import com.example.kotlinback.member.dto.LoginDto
import com.example.kotlinback.member.dto.MemberDto
import com.example.kotlinback.member.dto.SignUpDto

interface MemberService {
    fun signUp(signUpDto: SignUpDto)
    fun login(loginDto: LoginDto): Map<String, String>
    fun confirmMemberByNickname(nickname: String)
    fun confirmMemberByUsername(username: String)
    fun getByUsername(username: String): MemberDto
    fun getAccessTokenWithRefreshToken(refreshToken: String): String
    fun signOut(memId: String)
    fun sendEmailAndSaveTempData(email: String)
    fun confirmEmailAndCode(email: String, code: String)
}
