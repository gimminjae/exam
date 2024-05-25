package com.boblogservice.member.service;

import java.util.Map;

import com.boblogservice.member.dto.LoginDto;
import com.boblogservice.member.dto.MemberDto;
import com.boblogservice.member.dto.SignUpDto;
import com.boblogservice.member.entity.Member;

public interface MemberService {

    void signUp(SignUpDto signUpDto);

    Map<String, String> login(LoginDto loginDto);

    void confirmMemberByNickname(String nickname);

    void confirmMemberByUsername(String username);

    MemberDto getByUsername(String username);

    String getAccessTokenWithRefreshToken(String refreshToken);

    void signOut(String memId);
}
