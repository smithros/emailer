package com.example.springmail.service;

import com.example.springmail.service.api.EmailServiceApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService implements EmailServiceApi {

    final JavaMailSender mailSender;

    @Value("${username}")
    private String userName;

    public EmailSenderService(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(final String to, final String subject, final String text) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(userName);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
