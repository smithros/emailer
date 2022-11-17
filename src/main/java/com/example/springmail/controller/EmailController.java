package com.example.springmail.controller;

import com.example.springmail.controller.api.EmailControllerApi;
import com.example.springmail.service.EmailInboxService;
import com.example.springmail.service.api.EmailServiceApi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class EmailController implements EmailControllerApi {

    private static final String GMAIL_SENT_MAIL = "[Gmail]/Sent Mail";
    private static final String GMAIL_INBOX = "INBOX";

    private final EmailServiceApi emailServiceApi;
    private final EmailInboxService emailInboxService;

    public EmailController(final EmailServiceApi emailServiceApi, final EmailInboxService emailInboxService) {
        this.emailServiceApi = emailServiceApi;
        this.emailInboxService = emailInboxService;
    }

    @Override
    public String sendEmail(final String to, final String subject, final String text) {
        this.emailServiceApi.sendEmail(to, subject, text);
        return "send-email";
    }

    @Override
    public String getInbox(final Model model) {
        model.addAttribute("mails", this.emailInboxService.getEmailFromFolder(GMAIL_INBOX));
        return "inbox";
    }

    @Override
    public String getSent(final Model model) {
        model.addAttribute("mails", this.emailInboxService.getEmailFromFolder(GMAIL_SENT_MAIL));
        return "sent";
    }

    @Override
    public String getEmailById(final Integer id, final Model model) {
        model.addAttribute("mail", this.emailInboxService.getEmail(id));
        return "get-email";
    }
}
