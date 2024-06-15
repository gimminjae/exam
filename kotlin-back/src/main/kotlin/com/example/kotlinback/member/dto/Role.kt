package com.example.kotlinback.member.dto

import lombok.Getter

@Getter
enum class Role(private val role: String) {
    MEMBER("MEMBER"),
    ADMIN("ADMIN"),
    SUBADMIN("SUBADMIN")
}
