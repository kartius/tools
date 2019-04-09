package com.kartius.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClientException;

@Configuration
@EnableAutoConfiguration
@Controller
@Slf4j
public class TestController {

    @GetMapping("/login")
    public String login() throws RestClientException {
        return "login";
    }

    @GetMapping("/listener")
    public String telephonelogin() throws RestClientException {
        return "listener";
    }
}
