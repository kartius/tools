package com.kartius.service;


import com.kartius.data.Store;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventService {

    public void specifyEventType(String type, String userName) {
        Store.currentType = type;
        Store.userName = userName;
    }

    public void cleanEvent() {
        log.info("Clean events");
        Store.currentType = null;
        Store.userName = null;
    }


}
