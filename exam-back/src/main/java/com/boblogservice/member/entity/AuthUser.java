package com.boblogservice.member.entity;

import com.boblogservice.member.dto.MemberDto;
import com.boblogservice.member.dto.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@JsonIncludeProperties({"nickname", "role", "createDateTime", "memId", "username"})
public class AuthUser extends User {
    private final String memId;
    private final String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime createDateTime;
    private final String role;
    private final String username;

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return new HashSet<>(super.getAuthorities());
    }
    public AuthUser(MemberDto memberDto, List<GrantedAuthority> authorities) {
        super(memberDto.getMemId(), memberDto.getPassword(), authorities);
        this.memId = memberDto.getMemId();
        this.nickname = memberDto.getNickname();
        this.role = authorities.get(0).toString();
        this.createDateTime = memberDto.getCreateDateTime();
        this.username = memberDto.getUsername();
    }
}
