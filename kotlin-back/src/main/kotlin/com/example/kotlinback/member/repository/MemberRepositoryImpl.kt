package com.example.kotlinback.member.repository

import com.example.kotlinback.k.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepositoryImpl : MemberRepository, JpaRepository<Member?, String?>
