package com.boblogservice.member.repository;

import com.boblogservice.member.dto.MemberDto;
import com.boblogservice.member.dto.MemberType;
import com.boblogservice.member.dto.Role;
import com.boblogservice.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Sql("/sql/member-repository-test.sql")
@TestPropertySource("classpath:test-application.yml")
public class MemberRepositoryTests {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private MemberRepository memberRepository;

    private final String alreadyMemId = "20231104Me3cf1f7b-a715-4a8b-864a-b69826498c58";
    private final String alreadyNickname = "testuser";
    private final String alreadyUsername = "testuser";

    @Test
    @DisplayName("회원 생성")
    void save() {
        //given
        MemberDto memberDto = MemberDto.builder()
                .username("testuser2")
                .nickname("testuser2")
                .password("testuser200!")
                .memType("COMMON")
                .build();
        //password encode는 service단에서 진행
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        //when
        memberRepository.save(Member.from(memberDto));
        Member member = memberRepository.findByUsername("testuser2").get();
        Member member1 = memberRepository.findByUsername("testuser").get();

        //then
        assertThat(member.getMemId()).isNotNull();
        assertThat(member.getNickname()).isEqualTo(memberDto.getNickname());
        assertThat(member.getUsername()).isEqualTo(memberDto.getUsername());
        assertThat(member.getUseYn()).isTrue();
        assertThat(member.getCreateDateTime()).isNotNull();
        assertThat(member.getUpdateDateTime()).isNotNull();
        assertThat(member.getRole()).isEqualTo(Role.MEMBER);
        assertThat(member.getMemType()).isEqualTo(MemberType.COMMON);
    }
    @Test
    @DisplayName("회원 조회 by memId")
    void findById() {

    }
    @Test
    @DisplayName("회원 조회 by username")
    void findByUsername() {

    }
    @Test
    @DisplayName("회원 조회 by nickname")
    void findByNickname() {

    }
    @Test
    @DisplayName("회원 수정")
    void saveUpdate() {

    }
    @Test
    @DisplayName("회원 삭제")
    void delete() {

    }
}
