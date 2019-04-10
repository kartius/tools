package com.kartius.integration.gateway;


import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@MessagingGateway(defaultRequestChannel = "textInChannel", defaultReplyTimeout = "1000", defaultReplyChannel = "textInChannel")
public interface FileWriterGateway {

    void writeToFile(@Header(FileHeaders.FILENAME) String filename, String data);
}
