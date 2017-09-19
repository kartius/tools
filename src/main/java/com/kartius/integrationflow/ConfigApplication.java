package com.kartius.integrationflow;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class ConfigApplication {

    //redis
    @Value("${redis.url}")
    private String redisUrl;
    @Value("${redis.port}")
    private String redisPort;
    @Value("${redis.expiration}")
    private String redisExpiration;

//    //sntp
//    @Value("${email.sender}")
//    private String emailSender;
//    @Value("${email.receiver}")
//    private String emailReceiver;
//    @Value("${email.subject}")
//    private String emailSubject;
//    @Value("${email.message}")
//    private String emailMessage;



    public String getRedisUrl() {
        return redisUrl;
    }

    public void setRedisUrl(String redisUrl) {
        this.redisUrl = redisUrl;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisExpiration() {
        return redisExpiration;
    }

    public void setRedisExpiration(String redisExpiration) {
        this.redisExpiration = redisExpiration;
    }


//    public String getEmailSender() {
//        return emailSender;
//    }
//
//    public void setEmailSender(String emailSender) {
//        this.emailSender = emailSender;
//    }
//
//    public String getEmailReceiver() {
//        return emailReceiver;
//    }
//
//    public void setEmailReceiver(String emailReceiver) {
//        this.emailReceiver = emailReceiver;
//    }
//
//    public String getEmailSubject() {
//        return emailSubject;
//    }
//
//    public void setEmailSubject(String emailSubject) {
//        this.emailSubject = emailSubject;
//    }
//
//    public String getEmailMessage() {
//        return emailMessage;
//    }
//
//    public void setEmailMessage(String emailMessage) {
//        this.emailMessage = emailMessage;
//    }
}
