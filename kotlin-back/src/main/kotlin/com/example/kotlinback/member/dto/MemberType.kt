package com.example.kotlinback.member.dto

import lombok.Getter

@Getter
enum class MemberType(private val code: String, private val typeName: String) {
    KAKAO("K", "kakao"),
    NAVER("N", "naver"),
    GOOGLE("G", "google"),
    COMMON("C", "common")
}
