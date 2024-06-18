package com.example.kotlinback.global.mail.service;

import com.example.kotlinback.global.mail.dto.MailTo;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleEmailService {
    private final JavaMailSender javaMailSender;

    public void sendEmail(MailTo mailTo) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setSubject(mailTo.getTitle());
        simpleMailMessage.setText(mailTo.getMessage());
        simpleMailMessage.setTo(mailTo.getAddress());
        simpleMailMessage.setFrom("exam@exam.com");

        javaMailSender.send(simpleMailMessage);
    }
}
