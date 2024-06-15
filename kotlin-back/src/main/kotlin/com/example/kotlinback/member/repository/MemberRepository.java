package com.example.kotlinback.member.repository;

import com.example.kotlinback.member.entity.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findByUsername(String username);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findById(String id);

    Optional<Member> findByEmail(String email);
}
