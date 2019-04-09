package com.kartius.websocket;

import com.kartius.data.DataEvent;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;

import static java.lang.Thread.sleep;

/**
 * @author nikolay.tsyb
 */

@Slf4j
public class WebSocketClient {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public void sendEvent(DataEvent dataEvent) {
        if (dataEvent.getType().equals("one")) {
            messagingTemplate.convertAndSendToUser("user", "/topic/eventsOne/", dataEvent);
        } else {
            messagingTemplate.convertAndSendToUser("user", "/topic/eventsTwo/", dataEvent);
        }
    }
}
