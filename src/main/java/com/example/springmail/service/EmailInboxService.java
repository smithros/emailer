package com.example.springmail.service;

import com.example.springmail.dto.EmailDto;
import com.example.springmail.service.api.EmailInboxApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Service
public class EmailInboxService implements EmailInboxApi {

    @Value("${username}")
    private String userName;
    @Value("${password}")
    private String password;

    @Override
    public List<EmailDto> getInbox() {
        Message[] messages;
        List<EmailDto> mailList = new ArrayList<>();
        try {
            Properties properties = new Properties();
            properties.put("mail.pop3.host", "pop.gmail.com");
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");

            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("pop3s");
            store.connect( "pop.gmail.com", userName, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            messages = emailFolder.getMessages();
            System.out.println(messages.length);
            for (int i = messages.length-1; i >= 250; i--) {
                Message msg = messages[i];
                mailList.add(
                        EmailDto.builder()
                                .id(msg.getMessageNumber())
                                .from(Arrays.toString(msg.getFrom()))
                                .to(Arrays.toString(msg.getAllRecipients()))
                                .text(msg.getDescription())
                                .subject(msg.getSubject())
                                .sentDate(msg.getSentDate())
                                .receivedDate(msg.getReceivedDate())
                                .build()
                );
            }

            mailList.forEach(System.out::println);
            emailFolder.close(true);
            store.close();
            return mailList;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return mailList;
    }

    @Override
    public EmailDto getEmail(Integer id) {
        return null;
    }
}
