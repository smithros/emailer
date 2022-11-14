package com.example.springmail.controller;

import com.example.springmail.controller.api.EmailControllerApi;
import com.example.springmail.service.EmailInboxService;
import com.example.springmail.service.api.EmailServiceApi;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController implements EmailControllerApi {

    private final EmailServiceApi emailServiceApi;
    private final EmailInboxService emailInboxService;

    public EmailController(final EmailServiceApi emailServiceApi, EmailInboxService emailInboxService) {
        this.emailServiceApi = emailServiceApi;
        this.emailInboxService = emailInboxService;
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        emailServiceApi.sendEmail(to, subject, text);
    }

    @Override
    public void getInbox() {
        emailInboxService.getInbox();
    }

    @Override
    public void getEmailById(Integer id) {

    }
}
