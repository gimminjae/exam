package com.example.kotlinback.member.authcode;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface AuthCodeRedisRepository extends CrudRepository<AuthCode, String> {
}
