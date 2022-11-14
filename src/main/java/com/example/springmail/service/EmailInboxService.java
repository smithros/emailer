package com.example.springmail.service;

import com.example.springmail.dto.EmailDto;
import com.example.springmail.service.api.EmailInboxApi;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Service
public class EmailInboxService implements EmailInboxApi {

    private static final int EMAILS_SHOWN = 30;
    private static final String TEXT_PLAIN = "text/plain";
    private static final String MULTIPART = "multipart/*";
    private static final String EMPTY = "";

    @Value("${username}")
    private String userName;
    @Value("${password}")
    private String password;

    @Override
    public List<EmailDto> getEmailFromFolder(final String folderName) {
        List<EmailDto> mailList = new ArrayList<>();
        try {
            final Store store = this.getSession().getStore("imaps");
            store.connect("pop.gmail.com", userName, password);

            final Folder emailFolder = store.getFolder(folderName);
            emailFolder.open(Folder.READ_ONLY);

            final Message[] messages = emailFolder.getMessages();
            System.out.printf("Messages in folder: %d%n", messages.length);

            mailList = this.getMailList(messages);

            emailFolder.close(true);
            store.close();

            return mailList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mailList;
    }

    private List<EmailDto> getMailList(final Message[] messages) throws Exception {
        final List<EmailDto> mailList = new ArrayList<>();
        for (int i = messages.length - 1; i >= messages.length - EMAILS_SHOWN; i--) {
            final Message msg = messages[i];
            mailList.add(
                EmailDto.builder()
                    .id(msg.getMessageNumber())
                    .from(Arrays.toString(msg.getFrom()))
                    .to(Arrays.toString(msg.getAllRecipients()))
                    .text(this.getTextFromMessage(msg))
                    .subject(msg.getSubject())
                    .sentDate(msg.getSentDate())
                    .receivedDate(msg.getReceivedDate())
                    .build()
            );
        }
        return mailList;
    }

    @Override
    public EmailDto getEmail(Integer id) {
        return null;
    }

    private Session getSession() {
        final Properties properties = new Properties();
        properties.put("mail.pop3.host", "pop.gmail.com");
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");
        properties.put("mail.store.protocol", "imaps");
        return Session.getDefaultInstance(properties);
    }

    private String getTextFromMessage(final Message message) throws Exception {
        if (message.isMimeType(TEXT_PLAIN)) {
            return message.getContent().toString();
        } else if (message.isMimeType(MULTIPART)) {
            String result = EMPTY;
            final MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            int count = mimeMultipart.getCount();
            for (int i = 0; i < count; i++) {
                final BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType(TEXT_PLAIN)) {
                    result = result + "\n" + bodyPart.getContent();
                    break;
                } else if (bodyPart.isMimeType(TEXT_PLAIN)) {
                    final String html = (String) bodyPart.getContent();
                    result = result + "\n" + Jsoup.parse(html).text();
                }
            }
            return result;
        }
        return EMPTY;
    }
}
