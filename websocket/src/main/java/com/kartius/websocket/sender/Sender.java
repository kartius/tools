package com.kartius.websocket.sender;

import com.kartius.data.DataEvent;
import com.kartius.data.Store;
import com.kartius.websocket.WebSocketClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Sender {

    @Autowired
    private WebSocketClient webSocketClient;

    @Scheduled(fixedRateString = "2000")
    public void send() {
        if (Store.currentType != null) {
            log.info("send event with type " + Store.currentType);
            webSocketClient.sendEvent(new DataEvent(String.valueOf(1), 1 + "_test_" + 1, Store.currentType, Store.userName));
        }
    }


}
