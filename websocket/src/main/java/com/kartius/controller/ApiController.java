package com.kartius.controller;

import com.kartius.data.RequestEvent;
import com.kartius.service.EventService;
import com.kartius.websocket.WebSocketClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
public class ApiController {

    @Autowired
    WebSocketClient webSocketClient;

    @Autowired
    EventService eventService;


    @PostMapping("/event/send")
    public ResponseEntity<Void> send(@RequestBody RequestEvent requestEvent, Principal principal) {
        eventService.specifyEventType(requestEvent.getType(), principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/event/clean")
    public ResponseEntity<Void> clean() {
        eventService.cleanEvent();
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
