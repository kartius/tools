package com.kartius.kafka.controller;

import com.kartius.kafka.data.DataEvent;
import com.kartius.kafka.streams.publishing.DataPublisher;
import com.kartius.kafka.streams.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rest")
public class DataController {

    @Autowired
    private DataPublisher dataPublisher;

    @Autowired
    private StoreService storeService;

    @PostMapping("/publish")
    public ResponseEntity<Void> agentLogin(@RequestBody DataEvent event) {
        dataPublisher.publish(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getEventsCount() {
        return new ResponseEntity<>(storeService.getCountEvents(),HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity<List<DataEvent>> getEvents() {
        return new ResponseEntity<>(storeService.getAllEvents(),HttpStatus.OK);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<DataEvent> getEvent(@PathVariable String id) {
        return new ResponseEntity<>(storeService.getEvent(id),HttpStatus.OK);
    }
}
