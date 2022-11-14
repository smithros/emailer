package com.example.springmail.service.api;

public interface EmailServiceApi {
    void sendEmail(String to, String subject, String text);
}
