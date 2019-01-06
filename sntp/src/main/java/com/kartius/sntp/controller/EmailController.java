package com.kartius.sntp.controller;

import com.kartius.sntp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @CrossOrigin
    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public void sendEmail() {
        emailService.sendEmail();

    }
}
