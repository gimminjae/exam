package com.example.kotlinback.member.service;

import com.example.kotlinback.member.dto.LoginDto;
import com.example.kotlinback.member.dto.MemberDto;
import com.example.kotlinback.member.dto.SignUpDto;

import java.util.Map;

public interface MemberService {

    void signUp(SignUpDto signUpDto);

    Map<String, String> login(LoginDto loginDto);

    void confirmMemberByNickname(String nickname);

    void confirmMemberByUsername(String username);

    MemberDto getByUsername(String username);

    String getAccessTokenWithRefreshToken(String refreshToken);

    void signOut(String memId);

    void sendEmailAndSaveTempData(String email);

    void confirmEmailAndCode(String email, String code);
}
