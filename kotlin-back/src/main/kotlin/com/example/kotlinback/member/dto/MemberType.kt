package com.example.kotlinback.member.dto


enum class MemberType(private val code: String, private val typeName: String) {
    KAKAO("K", "kakao"),
    NAVER("N", "naver"),
    GOOGLE("G", "google"),
    COMMON("C", "common")
}
