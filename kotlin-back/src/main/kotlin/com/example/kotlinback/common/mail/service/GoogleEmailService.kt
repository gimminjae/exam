package com.example.kotlinback.common.mail.service

import com.example.kotlinback.common.mail.dto.MailTo
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Service

@Service
class GoogleEmailService(
) {
    private val javaMailSender: JavaMailSender = JavaMailSenderImpl()
    fun sendEmail(mailTo: MailTo) {
        val simpleMailMessage = SimpleMailMessage()
        simpleMailMessage.subject = mailTo.title
        simpleMailMessage.text = mailTo.message
        simpleMailMessage.setTo(mailTo.address)
        simpleMailMessage.from = "exam@exam.com"
        javaMailSender.send(simpleMailMessage)
    }
}
