package com.kartius.integrationflow.sntp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String link) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setFrom(new InternetAddress("kevinteamhackathon@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("kevinteamhackathon@gmail.com"));
            message.setSubject("New order");

            message.setText("Wake up ! " + link);

            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
