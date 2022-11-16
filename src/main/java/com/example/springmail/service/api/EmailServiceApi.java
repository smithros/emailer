package com.example.springmail.service.api;

public interface EmailServiceApi {
    void sendEmail(final String to, final String subject, final String text);
}
