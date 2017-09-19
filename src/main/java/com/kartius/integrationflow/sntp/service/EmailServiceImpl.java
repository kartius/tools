package com.kartius.integrationflow.sntp.service;

import com.kartius.integrationflow.sntp.SNTPConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SNTPConfig sntpConfig;

    public void sendEmail() {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setFrom(new InternetAddress(sntpConfig.getEmailSender()));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(sntpConfig.getEmailReceiver()));
                message.setSubject(sntpConfig.getEmailSubject());

            message.setText(sntpConfig.getEmailMessage());

            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
