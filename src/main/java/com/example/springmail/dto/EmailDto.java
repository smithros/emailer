package com.example.springmail.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Builder
@ToString
public class EmailDto {
    private final Integer id;
    private final String from;
    private final String to;
    private final String text;
    private final String subject;
    private final Date sentDate;
    private final Date receivedDate;
}
