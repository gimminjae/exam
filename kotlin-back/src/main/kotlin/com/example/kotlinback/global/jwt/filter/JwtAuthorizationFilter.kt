package com.example.kotlinback.global.jwt.filter

import com.example.kotlinback.global.jwt.provider.JwtProvider
import com.example.kotlinback.member.dto.MemberDto
import com.example.kotlinback.member.entity.AuthUser
import com.example.kotlinback.member.service.MemberService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthorizationFilter(
    private val jwtProvider: JwtProvider,
    private val memberService: MemberService
) : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val accessToken = request.getHeader("Authentication")
        if (accessToken != null && !accessToken.isEmpty()) {
            // 1차 체크(정보가 변조되지 않았는지 체크)
            if (jwtProvider.verify(accessToken)) {
                val claims = jwtProvider.getClaims(accessToken)
                val username = claims["username"] as String
                val memberDto = memberService.getByUsername(username)
                forceAuthentication(memberDto)
            }
        }
        filterChain.doFilter(request, response) //다음 필터를 실행시켜 주어야 한다
    }

    private fun forceAuthentication(memberDto: MemberDto) {
        val authUser = AuthUser(memberDto, memberDto.authorities)
        val authentication = UsernamePasswordAuthenticationToken.authenticated(
            authUser,
            null,
            authUser.authorities
        )
        val context = SecurityContextHolder.createEmptyContext()
        context.authentication = authentication
        SecurityContextHolder.setContext(context)
    }
}
