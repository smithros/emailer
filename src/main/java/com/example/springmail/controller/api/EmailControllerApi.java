package com.example.springmail.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface EmailControllerApi {

    @PostMapping("/sendEmail")
    void sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text);

    @GetMapping("/getInbox")
    void getInbox();

    @GetMapping("/email/{id}")
    void getEmailById(@PathVariable Integer id);
}
