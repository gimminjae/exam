package com.example.kotlinback.common.jwt.filter;

import com.example.kotlinback.common.jwt.provider.JwtProvider;
import com.example.kotlinback.member.dto.MemberDto;
import com.example.kotlinback.member.entity.AuthUser;
import com.example.kotlinback.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authentication");

        if (accessToken != null && !accessToken.isEmpty()) {
            // 1차 체크(정보가 변조되지 않았는지 체크)
            if (jwtProvider.verify(accessToken)) {
                Map<String, Object> claims = jwtProvider.getClaims(accessToken);
                String username = (String) claims.get("username");
                MemberDto memberDto = memberService.getByUsername(username);
                forceAuthentication(memberDto);
            }
        }
        filterChain.doFilter(request, response); //다음 필터를 실행시켜 주어야 한다
    }

    private void forceAuthentication(MemberDto memberDto) {
        AuthUser authUser = new AuthUser(memberDto, memberDto.getAuthorities());

        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        authUser,
                        null,
                        authUser.getAuthorities()
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
