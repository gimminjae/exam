package com.example.kotlinback.common.config

import com.example.kotlinback.common.jwt.filter.JwtAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthorizationFilter: JwtAuthorizationFilter
) {
    @Bean
    @Throws(Exception::class)
    fun apiFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { authorizeRequests ->
            authorizeRequests.requestMatchers(AntPathRequestMatcher("/**")).permitAll()
        }
            .formLogin { formLogin: FormLoginConfigurer<HttpSecurity> -> formLogin.disable() }
            .logout { logout: LogoutConfigurer<HttpSecurity> -> logout.disable() }
            .cors { cors: CorsConfigurer<HttpSecurity> -> cors.disable() }
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .sessionManagement { sessionManagement: SessionManagementConfigurer<HttpSecurity?> ->
                sessionManagement.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .addFilterBefore(
                jwtAuthorizationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
        return http.build()
    }
}
