package com.examback.member.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private String memId;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
    private String username;
    private String password;
    private String nickname;
    private String memType;
    private Boolean useYn;
    private String role;
    public static MemberDto from(SignUpDto signUpDto) {
        return MemberDto.builder()
                .username(signUpDto.getUsername())
                .nickname(signUpDto.getNickname())
                .password(signUpDto.getPassword1())
                .memType(signUpDto.getMemType())
                .build();
    }


    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(this.getRole().equals("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        } else if(this.getRole().equals("SUBADMIN")) {
            authorities.add(new SimpleGrantedAuthority("SUBADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("MEMBER"));
        }

        return authorities;
    }
}
