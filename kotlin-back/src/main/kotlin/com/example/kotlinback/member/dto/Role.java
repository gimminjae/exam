package com.example.kotlinback.member.dto;

import lombok.Getter;

@Getter
public enum Role {
    MEMBER("MEMBER"),
    ADMIN("ADMIN"),
    SUBADMIN("SUBADMIN");

    private String role;

    Role(String role) {
        this.role = role;
    }
}
