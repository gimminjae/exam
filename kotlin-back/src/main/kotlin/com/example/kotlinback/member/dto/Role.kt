package com.example.kotlinback.member.dto


enum class Role(private val role: String) {
    MEMBER("MEMBER"),
    ADMIN("ADMIN"),
    SUBADMIN("SUBADMIN")
}
