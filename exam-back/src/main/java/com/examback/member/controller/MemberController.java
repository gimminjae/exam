package com.examback.member.controller;

import com.examback.common.validation.ValidationUtil;
import com.examback.member.dto.LoginDto;
import com.examback.member.dto.SignUpDto;
import com.examback.member.entity.AuthUser;
import com.examback.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "MemberController API", description = "member")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "sign up - 회원가입", description = "")
    @PostMapping("")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpDto signUpDto, BindingResult bindingResult) {
        ValidationUtil.confirmError(bindingResult);
        memberService.signUp(signUpDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "confirm member by nickname - 닉네임 중복 확인", description = "")
    @Parameter(name = "nickname", description = "nickname - 닉네임")
    @GetMapping("/nickname")
    public ResponseEntity<Void> confirmNickname(@RequestParam String nickname) {
        memberService.confirmMemberByNickname(nickname);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "confirm member by username - 아이디 중복 확인", description = "")
    @Parameter(name = "username", description = "username - 아이디")
    @GetMapping("/username")
    public ResponseEntity<Void> confirmUsername(@RequestParam String username) {
        memberService.confirmMemberByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "sign in - 로그인", description = "")
    @GetMapping("/sign-in")
    public ResponseEntity<Map<String, String>> signIn(@ModelAttribute @Valid LoginDto loginDto, BindingResult bindingResult) {
        ValidationUtil.confirmError(bindingResult);
        return new ResponseEntity<>(memberService.login(loginDto), HttpStatus.OK);
    }
    @Operation(summary = "get accessToken with refreshToken - accessToken 재발급", description = "")
    @Parameter(name = "refreshToken", description = "refreshToken - 리프레시 토큰")
    @GetMapping("/access-token")
    public ResponseEntity<String> regenerateAccessToken(@RequestParam String refreshToken) {
        return new ResponseEntity<>(memberService.getAccessTokenWithRefreshToken(refreshToken), HttpStatus.OK);
    }
    @Operation(summary = "sign out - 로그아웃", description = "")
    @GetMapping("/sign-out")
    public ResponseEntity<String> regenerateAccessToken(@AuthenticationPrincipal AuthUser authUser) {
        memberService.signOut(authUser.getMemId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "get me - 로그인 회원 정보 조회", description = "")
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getMe(@AuthenticationPrincipal AuthUser authUser) {
        return new ResponseEntity<>(Map.of("member", authUser), HttpStatus.OK);
    }
}
