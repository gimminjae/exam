package com.example.kotlinback.member.entity;

import com.example.kotlinback.member.dto.MemberDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@JsonIncludeProperties({"nickname", "role", "createDateTime", "memId", "username"})
public class AuthUser extends User {
    private final String memId;
    private final String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime createDateTime;
    private final String role;
    private final String username;

    public AuthUser(MemberDto memberDto, List<GrantedAuthority> authorities) {
        super(memberDto.getMemId(), memberDto.getPassword(), authorities);
        this.memId = memberDto.getMemId();
        this.nickname = memberDto.getNickname();
        this.role = authorities.get(0).toString();
        this.createDateTime = memberDto.getCreateDateTime();
        this.username = memberDto.getUsername();
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return new HashSet<>(super.getAuthorities());
    }
}
