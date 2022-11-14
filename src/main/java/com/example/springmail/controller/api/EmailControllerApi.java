package com.example.springmail.controller.api;

import com.example.springmail.dto.EmailDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface EmailControllerApi {

    @PostMapping("/sendEmail")
    void sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text);

    @GetMapping("/getInbox")
    ResponseEntity<List<EmailDto>> getInbox();

    @GetMapping("/getSent")
    ResponseEntity<List<EmailDto>> getSent();

    @GetMapping("/email/{id}")
    ResponseEntity<EmailDto> getEmailById(@PathVariable Integer id);
}
