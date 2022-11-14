package com.example.springmail.service.api;

import com.example.springmail.dto.EmailDto;

import java.util.List;

public interface EmailInboxApi {
    List<EmailDto> getEmailFromFolder(String folderName);
    EmailDto getEmail(Integer id);
}
