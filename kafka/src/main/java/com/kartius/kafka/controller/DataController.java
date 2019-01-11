package com.kartius.kafka.controller;

import com.kartius.kafka.data.DataEvent;
import com.kartius.kafka.streams.publishing.DataPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/rest")
public class DataController {

    @Autowired
    private DataPublisher dataPublisher;

    @PostMapping("/publish")
    public ResponseEntity<Void> agentLogin(@RequestBody DataEvent event) {
        dataPublisher.publish(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
