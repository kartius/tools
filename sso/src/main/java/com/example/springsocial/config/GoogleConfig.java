package com.example.springsocial.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sso.google")
@Getter
@Setter
public class GoogleConfig {

    private String clientId;
    private String clientSecret;
}
