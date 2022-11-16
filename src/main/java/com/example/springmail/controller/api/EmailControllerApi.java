package com.example.springmail.controller.api;

import com.example.springmail.dto.EmailDto;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface EmailControllerApi {

    @PostMapping("/sendEmail")
    String sendEmail(@RequestParam final String to, @RequestParam final String subject, @RequestParam final String text);

    @GetMapping("/inbox")
    String getInbox(Model model);

    @GetMapping("/sent")
    String getSent(Model model);

    @GetMapping("/email/{id}")
    ResponseEntity<EmailDto> getEmailById(@PathVariable final Integer id);
}
