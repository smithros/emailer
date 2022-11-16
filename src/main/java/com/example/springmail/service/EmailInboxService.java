package com.example.springmail.service;

import com.example.springmail.dto.EmailDto;
import com.example.springmail.service.api.EmailInboxApi;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmailInboxService implements EmailInboxApi {

    private static final int EMAILS_SHOWN = 10;
    private static final String TEXT_PLAIN = "text/plain";
    private static final String MULTIPART = "multipart/*";
    private static final String EMPTY = "";

    private final Session session;

    @Value("${username}")
    private String userName;

    @Value("${password}")
    private String password;

    private List<EmailDto> mailList = new ArrayList<>();

    @Autowired
    public EmailInboxService(final Session session) {
        this.session = session;
    }

    @Override
    public List<EmailDto> getEmailFromFolder(final String folderName) {
        this.mailList = new ArrayList<>();
        try {
            final Store store = this.session.getStore("imaps");
            store.connect("pop.gmail.com", userName, password);

            final Folder emailFolder = store.getFolder(folderName);
            emailFolder.open(Folder.READ_ONLY);

            final Message[] messages = emailFolder.getMessages();
            System.out.printf("Messages in %s folder: %d%n", folderName, messages.length);

            this.mailList = this.getMailList(messages);

            emailFolder.close(true);
            store.close();

            return this.mailList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.mailList;
    }

    private List<EmailDto> getMailList(final Message[] messages) throws Exception {
        for (int i = messages.length - 1; i >= messages.length - EMAILS_SHOWN; i--) {
            final Message msg = messages[i];
            this.mailList.add(
                EmailDto.builder()
                    .id(msg.getMessageNumber())
                    .from(this.replace(Arrays.toString(msg.getFrom())))
                    .to(this.replace(Arrays.toString(msg.getAllRecipients())))
                    .text(this.getTextFromMessage(msg))
                    .subject(msg.getSubject())
                    .sentDate(msg.getSentDate())
                    .receivedDate(msg.getReceivedDate())
                    .build()
            );
        }
        return this.mailList;
    }

    private String replace(final String in) {
        return in.replaceAll("\\[","").replaceAll("]","");
    }

    @Override
    public EmailDto getEmail(final Integer id) {
        return this.mailList.get(id);
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
