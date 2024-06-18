package com.example.kotlinback.member.controller

import com.example.kotlinback.global.validation.ValidationUtil
import com.example.kotlinback.member.dto.LoginDto
import com.example.kotlinback.member.dto.SignUpDto
import com.example.kotlinback.member.entity.AuthUser
import com.example.kotlinback.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Tag(name = "MemberController API", description = "member")
@RestController
@RequestMapping("/api/member")
class MemberController(
    private val memberService: MemberService
) {
    @Operation(summary = "sign up - 회원가입", description = "")
    @PostMapping("")
    fun signUp(@RequestBody signUpDto: @Valid SignUpDto, bindingResult: BindingResult): ResponseEntity<Void> {
        ValidationUtil.confirmError(bindingResult)
        memberService.signUp(signUpDto)
        return ResponseEntity(HttpStatus.OK)
    }

    @Operation(summary = "confirm member by nickname - 닉네임 중복 확인", description = "")
    @Parameter(name = "nickname", description = "nickname - 닉네임")
    @GetMapping("/nickname")
    fun confirmNickname(@RequestParam nickname: String): ResponseEntity<Void> {
        memberService.confirmMemberByNickname(nickname)
        return ResponseEntity(HttpStatus.OK)
    }

    @Operation(summary = "confirm member by username - 아이디 중복 확인", description = "")
    @Parameter(name = "username", description = "username - 아이디")
    @GetMapping("/username")
    fun confirmUsername(@RequestParam username: String): ResponseEntity<Void> {
        memberService.confirmMemberByUsername(username)
        return ResponseEntity(HttpStatus.OK)
    }

    @Operation(summary = "sign in - 로그인", description = "")
    @GetMapping("/sign-in")
    fun signIn(
        @ModelAttribute loginDto: @Valid LoginDto,
        bindingResult: BindingResult
    ): ResponseEntity<Map<String, String>> {
        ValidationUtil.confirmError(bindingResult)
        return ResponseEntity(
            memberService.login(loginDto), HttpStatus.OK
        )
    }

    @Operation(summary = "get accessToken with refreshToken - accessToken 재발급", description = "")
    @Parameter(name = "refreshToken", description = "refreshToken - 리프레시 토큰")
    @GetMapping("/access-token")
    fun regenerateAccessToken(@RequestParam refreshToken: String): ResponseEntity<String> {
        return ResponseEntity(memberService.getAccessTokenWithRefreshToken(refreshToken), HttpStatus.OK)
    }

    @Operation(summary = "sign out - 로그아웃", description = "")
    @GetMapping("/sign-out")
    fun signOut(@AuthenticationPrincipal authUser: AuthUser): ResponseEntity<String> {
        memberService.signOut(authUser.memId)
        return ResponseEntity(HttpStatus.OK)
    }

    @Operation(summary = "get me - 로그인 회원 정보 조회", description = "")
    @GetMapping("")
    fun getMe(@AuthenticationPrincipal authUser: AuthUser): ResponseEntity<Map<String, AuthUser>> {
        return ResponseEntity(mapOf("member" to authUser), HttpStatus.OK)
    }

    @Operation(summary = "send email - 이메일 인증 코드 전송", description = "")
    @PostMapping("/email")
    fun sendEmail(@RequestParam email: String): ResponseEntity<Void> {
        memberService.sendEmailAndSaveTempData(email)
        return ResponseEntity(HttpStatus.OK)
    }

    @Operation(summary = "confirm email - 이메일 & 코드 인증", description = "")
    @GetMapping("/email-auth")
    fun sendEmail(@RequestParam email: String, @RequestParam code: String): ResponseEntity<Void> {
        memberService.confirmEmailAndCode(email, code)
        return ResponseEntity(HttpStatus.OK)
    }
}
