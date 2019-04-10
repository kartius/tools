package com.kartius.integration.gateway;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Sender {

    @Autowired
    FileWriterGateway fileWriterGateway;

    @Scheduled(fixedRateString = "2000")
    public void send() {
        log.info("send message");
        fileWriterGateway.writeToFile("1","jksdhfk");
    }

}
