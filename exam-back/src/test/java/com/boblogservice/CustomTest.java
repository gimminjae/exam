package com.boblogservice;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.UUID;

public class CustomTest {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Test
    void test() {
//        System.out.println("%s%s%s".formatted(
//                LocalDate.now().toString().replace("-", ""),
//                "M",
//                UUID.randomUUID().toString()
//        ));
        System.out.println(passwordEncoder.encode("testuser00!"));
    }
}
