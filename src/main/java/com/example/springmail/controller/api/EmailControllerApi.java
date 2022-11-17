package com.example.springmail.controller.api;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface EmailControllerApi {

    @PostMapping("/sendEmail")
    String sendEmail(@RequestParam final String to, @RequestParam final String subject, @RequestParam final String text);

    @GetMapping("/inbox")
    String getInbox(final Model model);

    @GetMapping("/sent")
    String getSent(final Model model);

    @GetMapping("/email/{id}")
    String getEmailById(@PathVariable final Integer id, final Model model);
}
