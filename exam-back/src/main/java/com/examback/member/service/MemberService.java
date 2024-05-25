package com.examback.member.service;

import java.util.Map;

import com.examback.member.dto.LoginDto;
import com.examback.member.dto.MemberDto;
import com.examback.member.dto.SignUpDto;

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
