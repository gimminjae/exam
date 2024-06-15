package com.example.kotlinback.member.dto;

import lombok.Getter;

@Getter
public enum MemberType {
    KAKAO("K", "kakao"),
    NAVER("N", "naver"),
    GOOGLE("G", "google"),
    COMMON("C", "common");

    private String code;
    private String typeName;

    MemberType(String code, String typeName) {
        this.code = code;
        this.typeName = typeName;
    }
}
