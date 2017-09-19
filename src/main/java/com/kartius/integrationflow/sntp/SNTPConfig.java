package com.kartius.integrationflow.sntp;

import com.kartius.integrationflow.ConfigApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ConfigApplication.class)
public class SNTPConfig {

    //sntp
    @Value("${email.sender}")
    private String emailSender;
    @Value("${email.receiver}")
    private String emailReceiver;
    @Value("${email.subject}")
    private String emailSubject;
    @Value("${email.message}")
    private String emailMessage;

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

    public String getEmailReceiver() {
        return emailReceiver;
    }

    public void setEmailReceiver(String emailReceiver) {
        this.emailReceiver = emailReceiver;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }
}
