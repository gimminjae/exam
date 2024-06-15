package com.example.kotlinback.member.repository;

import com.example.kotlinback.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepositoryImpl extends MemberRepository, JpaRepository<Member, String> {
}
