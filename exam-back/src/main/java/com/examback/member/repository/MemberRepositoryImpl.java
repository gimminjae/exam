package com.examback.member.repository;

import com.examback.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepositoryImpl extends MemberRepository, JpaRepository<Member, String> {
}
