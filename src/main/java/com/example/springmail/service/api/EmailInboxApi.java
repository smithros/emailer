package com.example.springmail.service.api;

import com.example.springmail.dto.EmailDto;

import java.util.List;

public interface EmailInboxApi {
    List<EmailDto> getInbox();

    EmailDto getEmail(Integer id);
}
